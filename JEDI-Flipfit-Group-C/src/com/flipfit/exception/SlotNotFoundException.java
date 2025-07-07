package com.flipfit.exception;

/**
 * Thrown when a specific time slot cannot be found in the database.
 */
public class SlotNotFoundException extends DAOException {

    /**
     * Constructs a new SlotNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public SlotNotFoundException(String message) {
        super(message);
    }
}
