package com.br.webshop.purchase.exception.recommendation;

import com.br.webshop.purchase.exception.AppException;

/**
 * Exception thrown when there is an error generating recommendations.
 */
public class GenerateRecommendationException extends AppException {
    /**
     * Constructs a new GenerateRecommendationException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GenerateRecommendationException(String message) {
        super(message);
    }

    /**
     * Constructs a new GenerateRecommendationException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public GenerateRecommendationException(String message, Throwable cause) {
        super(message, cause);
    }
}
