package com.krisjacyna.yasm.parse;

/**
 *
 * @author Kris Jacyna
 */
public class UndefinedElementException extends RuntimeException {

    /** UUID */
    private static final long serialVersionUID = -6840956399991979738L;
  
    private UndefinedElementException(final String element, final String name) {
        super(String.format("No %s found with ID '%s'", element, name));
    }
    
    public static UndefinedElementException undefinedAction(final String name) {
        return new UndefinedElementException("Action", name);
    }
    
    public static UndefinedElementException undefinedCondition(final String name) {
        return new UndefinedElementException("Condition", name);
    }
    
    public static UndefinedElementException undefinedState(final String name) {
        return new UndefinedElementException("State", name);
    }
}
