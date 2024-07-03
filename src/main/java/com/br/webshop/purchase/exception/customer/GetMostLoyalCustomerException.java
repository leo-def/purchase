package com.br.webshop.purchase.exception.customer;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error retrieving the most loyal customer.
 */
public class GetMostLoyalCustomerException extends AppException {
    /**
     * Constructs a new GetMostLoyalCustomerException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GetMostLoyalCustomerException(String message) {
        super(message);
    }

    /**
     * Constructs a new GetMostLoyalCustomerException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public GetMostLoyalCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
