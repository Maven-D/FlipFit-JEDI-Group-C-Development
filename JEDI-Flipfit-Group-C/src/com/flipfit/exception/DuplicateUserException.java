package com.flipfit.exception;

/**
 * Thrown when an attempt is made to create a user that already exists
 * (e.g., with a duplicate email or username).
 */
public class DuplicateUserException extends DAOException {

    /**
     * Constructs a new DuplicateUserException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DuplicateUserException(String message) {
        super(message);
    }
}
