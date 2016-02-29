package com.krisjacyna.yasm.parse;

/**
 *
 * @author Kris Jacyna
 */
public class MachineParseException extends RuntimeException {

    /** UUID */
    private static final long serialVersionUID = -7694200387146374448L;

    /**
     * @param message
     */
    public MachineParseException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public MachineParseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
