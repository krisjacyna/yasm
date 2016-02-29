package com.krisjacyna.yasm;

import java.util.Map;

/**
 * Represents a machine with a set of {@link State}s.
 * 
 * @author Kris Jacyna
 */
public class Machine {
    
    private final Map<String, State> states;
    
    private final State initialState;
    
    /**
     * Creates a {@link Machine} with the specified {@link State}s and the
     * initial {@link State}. to start on.
     * 
     * @param states the States
     * @param initialState the State to start on
     */
    public Machine(final Map<String, State> states, final State initialState) {
        this.states = states;
        this.initialState = initialState;
    }

    /**
     * Returns the map of {@link State}s in this {@link Machine}, keyed by ID.
     * 
     * @return the map of States
     */
    public Map<String, State> getStates() {
        return this.states;
    }

    /**
     * Returns the initial {@link State} for this {@link Machine}.
     * 
     * @return the initial State
     */
    public State getInitialState() {
        return this.initialState;
    }
}
