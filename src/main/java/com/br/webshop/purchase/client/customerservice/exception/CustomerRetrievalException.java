package com.br.webshop.purchase.client.customerservice.exception;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error retrieving customer information from the service.
 */
public class CustomerRetrievalException extends AppException {
    /**
     * Constructs a new CustomerRetrievalException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public CustomerRetrievalException(String message) {
        super(message);
    }

    /**
     * Constructs a new CustomerRetrievalException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public CustomerRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
