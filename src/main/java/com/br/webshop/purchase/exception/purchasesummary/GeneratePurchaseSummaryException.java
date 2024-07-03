package com.br.webshop.purchase.exception.purchasesummary;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error generating purchase summary.
 */
public class GeneratePurchaseSummaryException extends AppException {
    /**
     * Constructs a new GeneratePurchaseSummaryException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GeneratePurchaseSummaryException(String message) {
        super(message);
    }

    /**
     * Constructs a new GeneratePurchaseSummaryException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public GeneratePurchaseSummaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
