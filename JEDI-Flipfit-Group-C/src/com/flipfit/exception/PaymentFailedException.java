package com.flipfit.exception;

/**
 * Thrown when a payment processing operation fails.
 */
public class PaymentFailedException extends DAOException {

    /**
     * Constructs a new PaymentFailedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PaymentFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new PaymentFailedException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the underlying cause.
     */
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
