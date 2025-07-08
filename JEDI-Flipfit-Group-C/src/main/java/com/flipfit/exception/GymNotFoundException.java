package com.flipfit.exception;

/**
 * Thrown when a specific gym cannot be found in the database.
 */
public class GymNotFoundException extends DAOException {

    /**
     * Constructs a new GymNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public GymNotFoundException(String message) {
        super(message);
    }
}
