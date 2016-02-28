package com.krisjacyna.yasm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a {@link Transition} from one {@link State} to another.
 * 
 * A {@link Transition} is triggered by an event and will evaluate the
 * {@link Condition} if one has been set. When a {@link Transition} is
 * successful, any {@link Action}s will be executed before moving onto
 * the next {@link State}.
 *
 * @author Kris Jacyna
 */
public class Transition {
    
    private final String event;
    
    private final State target;
    
    private final Condition condition;
    
    private final List<Action> actions;

    private Transition(final Builder builder) {
        this.event = builder.event;
        this.target = builder.target;
        this.condition = builder.condition;
        this.actions  = builder.actions;
    }
    
    /**
     * Returns the event which triggers this {@link Transition}.
     * 
     * @return the event name
     */
    public String getEvent() {
        return this.event;
    }

    /**
     * Returns the target {@link State} if there is one.
     * 
     * @return an optional target state
     */
    public Optional<State> getTarget() {
        return Optional.ofNullable(this.target);
    }

    /**
     * Returns the {@link Condition} if there is one.
     * 
     * @return an optional Condition
     */
    public Optional<Condition> getCondition() {
        return Optional.ofNullable(this.condition);
    }

    /**
     * Returns the {@link List} of {@link Action}s (which may be empty).
     * 
     * @return the List of Actions
     */
    public List<Action> getActions() {
        return this.actions;
    }
    
    @Override
    public String toString() {
        return new StringBuilder()
                .append("Transition[")
                .append("event=").append(this.event)
                .append(", target=").append(this.target != null ? this.target.getId() : "none")
                .append(", condition=").append(this.condition != null ? this.condition.getId() : "none")
                .append(", actions=")
                .append(this.actions.stream().map(Action::getId).collect(Collectors.toList()))
                .append("]")
                .toString();
    }

    /**
     * Builder for creating {@link Transition}s.
     */
    public static class Builder {
        
        private final String event;
        private State target;
        private Condition condition;
        private final List<Action> actions = new ArrayList<>();
        
        /**
         * Creates a new {@link Builder} with the given event.
         * 
         * @param event the name of the event
         */
        public Builder(final String event) {
            this.event = event;
        }
        
        public Builder setTarget(final State target) {
            this.target = target;
            return this;
        }
        
        public Builder setCondition(final Condition condition) {
            this.condition = condition;
            return this;
        }

        public Builder addAction(final Action action) {
            this.actions.add(action);
            return this;
        }
        
        public Transition build() {
            return new Transition(this);
        }
    }
}
