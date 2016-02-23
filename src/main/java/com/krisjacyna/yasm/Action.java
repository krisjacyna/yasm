package com.krisjacyna.yasm;

/**
 * Represents an {@link Action} to perform operations on a {@link Context}.
 * 
 * @author Kris Jacyna
 */
public interface Action extends Identifiable {

    /**
     * Executes this {@link Action} on the given {@link Context}.
     * 
     * @param context the Context to action on
     */
    void execute(final Context context);
}
