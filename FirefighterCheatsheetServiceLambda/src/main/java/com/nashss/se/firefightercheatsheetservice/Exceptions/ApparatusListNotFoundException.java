package com.nashss.se.firefightercheatsheetservice.Exceptions;

/**
 * Exception to throw when apparatus list is not found in the database.
 */

public class ApparatusListNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -912326717789387971L;

    /**
     * Exception with no message or cause.
     */
    public ApparatusListNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ApparatusListNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ApparatusListNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ApparatusListNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
