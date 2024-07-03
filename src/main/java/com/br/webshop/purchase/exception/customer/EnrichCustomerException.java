package com.br.webshop.purchase.exception.customer;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error enriching customer data.
 */
public class EnrichCustomerException extends AppException {
    /**
     * Constructs a new EnrichCustomerException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public EnrichCustomerException(String message) {
        super(message);
    }

    /**
     * Constructs a new EnrichCustomerException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public EnrichCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
