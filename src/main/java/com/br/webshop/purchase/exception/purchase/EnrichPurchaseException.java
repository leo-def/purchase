package com.br.webshop.purchase.exception.purchase;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error enriching purchase data.
 */
public class EnrichPurchaseException extends AppException {
    /**
     * Constructs a new EnrichPurchaseException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public EnrichPurchaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new EnrichPurchaseException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public EnrichPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
