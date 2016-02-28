package com.krisjacyna.yasm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a state in the machine.
 * 
 * A state has a unique ID and may contain sets of {@link Action}s which
 * are executed when it is entered or exited. It may also contain a set of
 * {@link Transition}s which allow the machine to move onto other {@link State}s
 * when events are triggered.
 * 
 * If a {@link State} is marked as being 'final' then the machine cannot move
 * from it regardless of which events are triggered. Each machine must have only
 * one {@link State} marked as 'initial' which is the entry point for the machine.
 * 
 * @author Kris Jacyna
 */
public class State {
    
    private final String id;

    private final boolean isInitial;
    
    private final boolean isFinal;
    
    private final List<Action> entryActions = new ArrayList<>();
    
    private final List<Action> exitActions = new ArrayList<>();
    
    private final List<Transition> transitions = new ArrayList<>();

    /**
     * Creates a new {@link State} with the specified ID and flags.
     * 
     * @param id the Id of the State
     * @param isInitial whether or not the State is the initial
     * @param isFinal whether or not the state is a final state
     */
    public State(final String id, final boolean isInitial, final boolean isFinal) {
        this.id = id;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
    }

    /**
     * Returns the ID of this {@link State}.
     * 
     * @return the ID of this State
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Returns whether of not this {@link State} is the initial point of entry.
     * 
     * @return {@code true} if this {@link State} is marked as initial,
     *         {@code false} otherwise
     */
    public boolean isInitial() {
        return this.isInitial;
    }

    /**
     * Returns whether of not this {@link State} is a final state.
     * 
     * @return {@code true} if this {@link State} is marked as final,
     *         {@code false} otherwise
     */
    public boolean isFinal() {
        return this.isFinal;
    }

    /**
     * Adds an {@link Action} to be executed when this {@link State} is entered.
     * 
     * @param action the Action to execute on entry
     */
    public void addEntryAction(final Action action) {
        this.entryActions.add(action);
    }
    
    /**
     * Adds an {@link Action} to be executed when this {@link State} is exited.
     * 
     * @param action the Action to execute on exit
     */
    public void addExitAction(final Action action) {
        this.exitActions.add(action);
    }
    
    /**
     * Adds a {@link Transition} to allow the machine to move to other
     * {@link State}s when events are triggered.
     * 
     * @param transition the Transition to add
     */
    public void addTransition(final Transition transition) {
        this.transitions.add(transition);
    }
    
    /**
     * Returns the {@link List} of {@link Action}s to execute on entry.
     * 
     * @return the List of entry Actions
     */
    public List<Action> getEntryActions() {
        return this.entryActions;
    }

    /**
     * Returns the {@link List} of {@link Action}s to execute on exit.
     * 
     * @return the List of exit Actions
     */
    public List<Action> getExitActions() {
        return this.exitActions;
    }

    /**
     * Returns a {@link List} of {@link Transition}s to be followed for the
     * specific event {@link String}. This will be empty if there are no matches.
     * 
     * @param event the event trigger
     * @return a List of applicable Transitions
     */
    public List<Transition> getTransitions(final String event) {
        return this.transitions.stream()
            .filter(t -> event.equals(t.getEvent()))
            .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("State [id=").append(this.id)
                .append(", isInitial=").append(this.isInitial)
                .append(", isFinal=").append(this.isFinal)
                .append(", entryActions=")
                .append(this.entryActions.stream().map(Action::getId).collect(Collectors.toList()))
                .append(", exitActions=")
                .append(this.exitActions.stream().map(Action::getId).collect(Collectors.toList()))
                .append(", transitions=").append(this.transitions)
                .append("]")
                .toString();
    }
}
