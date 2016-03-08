package com.krisjacyna.yasm;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Kris Jacyna
 */
public class MachineExecutor {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final Machine machine;
    
    private State currentState;
    
    private final Context context;
    
    private final Set<TransitionListener> listeners = new CopyOnWriteArraySet<>();

    public MachineExecutor(final Machine machine, final Context context) {
        this.machine = machine;
        this.context = context;
        reset();
    }

    public void reset() {
        this.currentState = this.machine.getInitialState();
    }
    
    public void start() {
        this.currentState.getEntryActions().forEach(a -> a.execute(this.context));
    }
    
    public void addListener(final TransitionListener listener) {
        this.listeners.add(listener);
    }
    
    public void fire(final String event) {
        this.logger.info("firing event {} on {}", event, this.currentState.getId());
        if (this.currentState.isFinal()) {
            this.logger.info("{} is in a final state", this.currentState.getId());
            return;
        }
        final List<Transition> transitions = this.currentState.getTransitions(event);
        if (transitions.isEmpty()) {
            this.logger.info("no transition for event {}", event);
            return;
        }
        for (final Transition trans : transitions) {
            if (evaluateCondition(trans)) {
                
                if (trans.getTarget().isPresent()) {
                    // do exit actions
                    this.currentState.getExitActions().forEach(a -> a.execute(this.context));
                }
                
                // do trans actions
                trans.getActions().forEach(a -> a.execute(this.context));
                
                // change state
                if (trans.getTarget().isPresent()) {
                    final State oldState = this.currentState;
                    this.currentState = trans.getTarget().get();
                    // notify listeners
                    this.listeners.forEach(l -> l.onTransition(oldState, this.currentState, event));
                    // do entry actions
                    this.currentState.getEntryActions().forEach(a -> a.execute(this.context));
                }
                return;
            }
        }
    }
    
    private boolean evaluateCondition(final Transition transition) {
        if (transition.getCondition().isPresent()) {
            return transition.getCondition().get().evaluate(this.context);
        }
        return true;
    }
}
