package com.br.webshop.purchase.dto.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * API purchase DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Purchase Information")
public class PurchaseDTO {
    /**
     * Purchase product quantity
     */
    @Schema(description = "Purchase Quantity", example = "3")
    private Integer quantity;

    /**
     * Purchase total value
     */
    @Schema(description = "Purchase Total Value", example = "365.25")
    private Double totalCost;

    /**
     * Purchase product
     */
    @Schema(description = "Purchase Product")
    private ProductDTO product;
}
