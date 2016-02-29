package com.krisjacyna.yasm.parse;

/**
 *
 * @author Kris Jacyna
 */
public class MachineValidationException extends RuntimeException {

    /** UUID */
    private static final long serialVersionUID = -7183958956785785641L;

    /**
     * @param message
     */
    public MachineValidationException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public MachineValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
