package com.br.webshop.purchase.utils;


import com.br.webshop.purchase.dto.ApiErrorResponseDTO;
import com.br.webshop.purchase.dto.ApiResponseDTO;

/**
 * Util class for handling standard api response.
 */
public class ApiResponseUtils {

    /**
     * Generates a success response with data.
     *
     * @param data Data to be returned in the response.
     * @param <T>  Type of the response data.
     * @return ApiResponseDTO containing the data.
     */
    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(true, "Request was successful", data);
    }

    /**
     * Generates a success response with a custom message.
     *
     * @param message Custom success message.
     * @param <T>     Type of the response data.
     * @return ApiResponseDTO with the custom message.
     */
    public static <T> ApiResponseDTO<T> successMessage(String message) {
        return new ApiResponseDTO<>(true, message, null);
    }

    /**
     * Generates an error response with a message.
     *
     * @param message Error message.
     * @return ApiErrorResponseDTO containing the error message.
     */
    public static ApiErrorResponseDTO error(String message) {
        return new ApiErrorResponseDTO(message);
    }

    /**
     * Generates an error response with an exception.
     *
     * @param ex Exception ex.
     * @return ApiErrorResponseDTO containing the error message and data.
     */
    public static ApiErrorResponseDTO error(Exception ex) {
        return new ApiErrorResponseDTO(ex.getMessage());
    }
}
