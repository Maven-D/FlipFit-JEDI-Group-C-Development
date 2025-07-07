package com.flipfit.exception;

/**
 * Thrown when a booking operation fails for reasons other than a
 * not-found entity, such as no available seats.
 */
public class BookingFailedException extends DAOException {

    /**
     * Constructs a new BookingFailedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BookingFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new BookingFailedException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the underlying cause.
     */
    public BookingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
