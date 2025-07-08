package com.flipfit.exception;

/**
 * Thrown to indicate a general data access problem, typically wrapping a
 * {@link java.sql.SQLException}.
 */
public class DataAccessException extends DAOException {

    /**
     * Constructs a new DataAccessException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the underlying cause (usually a SQLException).
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
