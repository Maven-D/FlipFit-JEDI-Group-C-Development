package com.flipfit.exception;

/**
 * Base exception for all Data Access Object (DAO) related errors.
 * This is a runtime exception, as DAO errors are often unrecoverable
 * at the point they occur and should be handled by a higher-level
 * exception handler.
 */
public class DAOException extends RuntimeException {

    /**
     * Constructs a new DAOException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAOException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A {@code null} value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
