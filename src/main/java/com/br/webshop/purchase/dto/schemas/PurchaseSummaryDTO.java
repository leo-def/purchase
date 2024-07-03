package com.br.webshop.purchase.dto.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API Purchase Summary DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Purchases Summary Information")
public class PurchaseSummaryDTO {
    /**
     * Purchases count
     */
    @Schema(description = "Purchases Count", example = "1")
    private Integer count;

    /**
     * Purchases total value
     */
    @Schema(description = "Purchases Total Value", example = "365.25")
    private Double totalCost;
}