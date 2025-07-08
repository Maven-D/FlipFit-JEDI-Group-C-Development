package com.flipfit.exception;

/**
 * Thrown when a specific booking cannot be found in the database.
 */
public class BookingNotFoundException extends DAOException {

    /**
     * Constructs a new BookingNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BookingNotFoundException(String message) {
        super(message);
    }
}
