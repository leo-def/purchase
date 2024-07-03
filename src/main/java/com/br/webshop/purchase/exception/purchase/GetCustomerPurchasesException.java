package com.br.webshop.purchase.exception.purchase;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error retrieving customer purchases.
 */
public class GetCustomerPurchasesException extends AppException {
    /**
     * Constructs a new GetCustomerPurchasesException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GetCustomerPurchasesException(String message) {
        super(message);
    }

    /**
     * Constructs a new GetCustomerPurchasesException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public GetCustomerPurchasesException(String message, Throwable cause) {
        super(message, cause);
    }
}
