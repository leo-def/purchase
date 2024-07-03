package com.br.webshop.purchase.exception.product;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error retrieving product data.
 */
public class GetProductException extends AppException {
    /**
     * Constructs a new GetProductException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GetProductException(String message) {
        super(message);
    }

    /**
     * Constructs a new GetProductException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public GetProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
