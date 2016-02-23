package com.krisjacyna.yasm;

/**
 * Represents a boolean condition evaluating a {@link Context}.
 * 
 * @author Kris Jacyna
 */
public interface Condition extends Identifiable {
	
	/**
	 * Evaluates the given {@link Context}.
	 * 
	 * @param context the Context to evaluate
	 * @return {@code true} if the Context passes the condition, {@code false} otherwise
	 */
	boolean evaluate(final Context context);
}
