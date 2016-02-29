package com.krisjacyna.yasm.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.krisjacyna.yasm.Action;
import com.krisjacyna.yasm.Condition;
import com.krisjacyna.yasm.State;
import com.krisjacyna.yasm.Transition;

/**
 * 
 *
 * @author Kris Jacyna
 */
public class XmlHandler extends DefaultHandler {
        
    private final Map<String, Action> actionRegistry = new HashMap<>();
    
    private final Map<String, Condition> conditionRegistry = new HashMap<>();
    
    // State names to State objects
    private final Map<String, State> states = new HashMap<>();
    
    // Map of Origin states to a Map of target states to transitions
    // This is to keep track of transitions before all of the States
    // have been processed
    private final Map<String, Map<String, List<Transition.Builder>>> transitionBuilders = new HashMap<>();
    
    private final Stack<String> element = new Stack<>();

    // reusable objects
    private State rState;
    
    private Transition.Builder rTransitionBuilder;

    /**
     * 
     * @param actionRegistry
     * @param conditionRegistry
     */
    public XmlHandler(final Map<String, Action> actionRegistry,
            final Map<String, Condition> conditionRegistry) {
        this.actionRegistry.putAll(actionRegistry);
        this.conditionRegistry.putAll(conditionRegistry);
    }
    
    @Override
    public void startElement(final String uri, final String localName,
            final String qName, final Attributes attributes) throws SAXException {
        
        switch (qName) {
        case "state":
            final String id = attributes.getValue("id");
            if (this.states.containsKey(id)) {
                throw new MachineValidationException("Duplicate state with ID '" + id + "'");
            }
            
            final boolean isInitial = Boolean.valueOf(getOrDefault(attributes, "initial", "false"));
            final boolean isFinal = Boolean.valueOf(getOrDefault(attributes, "final", "false"));
            this.rState = new State(id, isInitial, isFinal);
            break;
            
        case "action":
            final String action = attributes.getValue("exec");
            if (!this.actionRegistry.containsKey(action)) {
                throw UndefinedElementException.undefinedAction(action);
            }
            if (this.element.peek().equals("entry")) {
                this.rState.addEntryAction(this.actionRegistry.get(action));
            }
            else if (this.element.peek().equals("exit")) {
                this.rState.addExitAction(this.actionRegistry.get(action));
            }
            else if (this.element.peek().equals("transition")) {
                this.rTransitionBuilder.addAction(this.actionRegistry.get(action));
            }
            break;
            
        case "transition":
            if (this.rState.isFinal()) {
                throw new MachineValidationException(String.format(
                        "State '%s' cannot define any transtitions as it is marked as final",
                        this.rState.getId()));
            }
            final String event = attributes.getValue("event");
            final String target = attributes.getValue("target");
            final String condition = attributes.getValue("condition");
            
            this.rTransitionBuilder = new Transition.Builder(event);
            if (condition != null) {
                if (!this.conditionRegistry.containsKey(condition)) {
                    throw UndefinedElementException.undefinedCondition(condition);
                }
                this.rTransitionBuilder.setCondition(this.conditionRegistry.get(condition));
            }
            if (target != null) {
                if (!this.transitionBuilders.containsKey(this.rState.getId())) {
                    this.transitionBuilders.put(this.rState.getId(), new HashMap<>());
                }
                if (!this.transitionBuilders.get(this.rState.getId()).containsKey(target)) {
                    this.transitionBuilders.get(this.rState.getId()).put(target, new ArrayList<>());
                }
                this.transitionBuilders.get(this.rState.getId()).get(target).add(this.rTransitionBuilder);
            }
            break;
           
        default:
            break;
        }
        this.element.push(qName);
    }

    @Override
    public void endElement(final String uri, final String localName,
            final String qName) throws SAXException {
        
        this.element.pop();
        switch (qName) {
        case "state":
            this.states.put(this.rState.getId(), this.rState);
            this.rState = null;
            break;

        case "machine":
            if (this.element.isEmpty()) {
                checkInitialState();
                buildTransitions();
            } else {
                // error
            }
            break;
            
        default:
            break;
        }
    }
    
    private void checkInitialState() {
        if (this.states.values().stream().filter(s -> s.isInitial()).count() != 1) {
            throw new MachineValidationException("No state is marked as initial");
        }
    }
    
    private void buildTransitions() throws SAXException {
        this.transitionBuilders.forEach((state, builderMap) -> {
            final State originState = this.states.get(state);
            builderMap.forEach((target, builders) -> {
                if (!this.states.containsKey(target)) {
                    throw UndefinedElementException.undefinedState(target);
                }
                final State targetState = this.states.get(target);
                builders.forEach(builder -> {
                    builder.setTarget(targetState);
                    originState.addTransition(builder.build());
                });
            });
        });
    }

    public Map<String, State> getStates() {
        return this.states;
    }
    
    public State getInitialState() {
        return this.states.values().stream().filter(s -> s.isInitial()).findFirst().get();
    }
    
    private static String getOrDefault(final Attributes attrs, final String key, final String def) {
        final String val = attrs.getValue(key);
        return val != null ? val : def;
    }
}
