package com.br.webshop.purchase.exception;

/**
 * Base exception class for application-specific exceptions.
 */
public abstract class AppException extends RuntimeException {
    /**
     * Constructs a new application exception with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * Constructs a new application exception with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public AppException(String message, Throwable cause) {
        super(constructMessage(message, cause), cause);
    }

    /**
     * Constructs the exception message, combining the message from the cause if it's an AppException.
     *
     * @param message The detail message to be displayed.
     * @param cause   The cause of the exception.
     * @return The constructed exception message.
     */
    private static String constructMessage(String message, Throwable cause) {
         if (cause instanceof AppException) {
            return cause.getMessage() + "\n\r" + message;
        } else {
            return message;
        }
    }
}