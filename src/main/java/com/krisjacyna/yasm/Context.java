package com.krisjacyna.yasm;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a simple context with local variables and an optional name.
 * 
 * @author Kris Jacyna
 */
public class Context {
    
    private final Map<String, Object> variables = new HashMap<>();
    
    private final String name;
    
    /**
     * Creates a new {@link Context} with the specific name.
     * 
     * @param name the name of the Context
     */
    public Context(final String name) {
        this.name = name;
    }
    
    /**
     * Creates a new {@link Context} with no name.
     */
    public Context() {
        this(null);
    }
    
    /**
     * Returns the name of this {@link Context}.
     * 
     * @return the name of this Context or {@code null}
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the specified value with the specified key for this {@link Context},
     * replacing any previously set value with this key.
     * 
     * @param key the key to use
     * @param value the value to store
     */
    public void set(final String key, final Object value) {
        this.variables.put(key, value);
    }
    
    /**
     * Returns the value for the specified key, or {@code null}
     * if this {@link Context} contains no mapping for the key.
     * 
     * @param key the key of the value
     * @return the value for the key, or {@code null} if there is no value
     */
    public Object get(final String key) {
        return this.variables.get(key);
    }
    
    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.name != null ? this.name : "UnnamedContext")
                .append(": [")
                .append(this.variables)
                .append("]")
                .toString();
    }
}
