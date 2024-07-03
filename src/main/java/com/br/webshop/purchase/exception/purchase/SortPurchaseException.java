package com.br.webshop.purchase.exception.purchase;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error sorting purchases.
 */
public class SortPurchaseException extends AppException {

    /**
     * Constructs a new SortPurchaseException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public SortPurchaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new SortPurchaseException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public SortPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
