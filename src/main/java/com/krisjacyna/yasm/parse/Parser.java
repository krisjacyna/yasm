package com.krisjacyna.yasm.parse;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.krisjacyna.yasm.Action;
import com.krisjacyna.yasm.Condition;
import com.krisjacyna.yasm.Machine;

/**
 * 
 *
 * @author Kris Jacyna
 */
public class Parser {
    
    private final SAXParserFactory parserFactor = SAXParserFactory.newInstance();
    
    private final Map<String, Action> actionRegistry = new HashMap<>();
    
    private final Map<String, Condition> conditionRegistry = new HashMap<>();
    
    private final File file;
    
    private final SAXParser parser;
    
    private Parser(final String url) throws ParserConfigurationException, SAXException {
        this.file = new File(url);
        this.parser = this.parserFactor.newSAXParser();
    }
    
    /**
     * 
     * @param url
     * @return
     */
    public static Parser newInstance(final String url) {
        try {
            return new Parser(url);
        }
        catch (final ParserConfigurationException|SAXException e) {
           throw new MachineParseException("Failed to create Parser instance", e);
        }
    }
    
    /**
     * 
     * @param action
     * @return
     */
    public Parser addAction(final Action action) {
        this.actionRegistry.put(action.getId(), action);
        return this;
    }
    
    /**
     * 
     * @param actions
     * @return
     */
    public Parser addActions(final Collection<Action> actions) {
        actions.forEach(a -> this.actionRegistry.put(a.getId(), a));
        return this;
    }
    
    /**
     * 
     * @param condition
     * @return
     */
    public Parser addCondition(final Condition condition) {
        this.conditionRegistry.put(condition.getId(), condition);
        return this;
    }
    
    /**
     * 
     * @param conditions
     * @return
     */
    public Parser addConditions(final Collection<Condition> conditions) {
        conditions.forEach(c -> this.conditionRegistry.put(c.getId(), c));
        return this;
    }
    
    /**
     * 
     * @return
     */
    public Machine parse() {
        try {
            final XmlHandler handler = new XmlHandler(this.actionRegistry, this.conditionRegistry);
            this.parser.parse(this.file, handler);
            return new Machine(handler.getStates(), handler.getInitialState());
        }
        catch (final Exception e) {
            throw new MachineParseException("Failed to parse " + this.file.getAbsolutePath(), e);
        }
    }
}
