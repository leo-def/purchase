package com.br.webshop.purchase.client.productservice.exception;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error retrieving product information from the service.
 */
public class ProductRetrievalException extends AppException {

    /**
     * Constructs a new ProductRetrievalException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ProductRetrievalException(String message) {
        super(message);
    }

    /**
     * Constructs a new ProductRetrievalException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public ProductRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
