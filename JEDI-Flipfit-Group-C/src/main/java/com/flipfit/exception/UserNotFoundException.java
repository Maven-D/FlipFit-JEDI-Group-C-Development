package com.flipfit.exception;

/**
 * Thrown when a specific user cannot be found in the database.
 */
public class UserNotFoundException extends DAOException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
