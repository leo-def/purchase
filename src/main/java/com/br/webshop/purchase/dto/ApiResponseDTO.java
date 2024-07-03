package com.br.webshop.purchase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * API response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API Response")
public class ApiResponseDTO<T> {
    /**
     * API response success indicator
     */
    @Schema(description = "API Response Success Indicator", example="true")
    private boolean success;

    /**
     * API response message
     */
    @Schema(description = "API Response Message", example="Successful executed")
    private String message;

    /**
     * API response data
     */
    @Schema(description = "API Response Data")
    private T data;
}
