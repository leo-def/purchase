package com.br.webshop.purchase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * API error response DTO
 */
@Data
@Builder
@NoArgsConstructor
@Schema(description = "API Response")
public class ApiErrorResponseDTO {
    /**
     * API error response success indicator
     */
    @Getter
    @Schema(description = "API Error Response Success Indicator", example="false")
    private final boolean success = false;

    /**
     * API error response message
     */
    @Schema(description = "API Error Response Message", example = "Request error")
    private String message;

    public ApiErrorResponseDTO(String message) {
        this.message = message;
    }
}
