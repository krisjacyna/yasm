package com.krisjacyna.yasm;

/**
 * Defines a listener to receive transition events between two {@link State}s.
 * 
 * @author Kris Jacyna
 */
public interface TransitionListener {
    
    /**
     * Called when a transition is invoked between two {@link State}s.
     * 
     * @param from the origin State
     * @param to the target State
     * @param event the name of the event
     */
    void onTransition(final State from, final State to, final String event);
}
