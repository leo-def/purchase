package com.br.webshop.purchase.exception.purchase;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error retrieving the largest purchase of a specific year.
 */
public class GetYearsLargestPurchaseException extends AppException {
    /**
     * Constructs a new GetYearsLargestPurchaseException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GetYearsLargestPurchaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new GetYearsLargestPurchaseException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public GetYearsLargestPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
