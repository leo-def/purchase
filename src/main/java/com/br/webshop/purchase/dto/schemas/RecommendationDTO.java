package com.br.webshop.purchase.dto.schemas;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * API Customer recommendations DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer Recommendations")

public class RecommendationDTO {

    /**
     * Recommendation customer
     */
    @Schema(description = "Customer")
    private CustomerDTO customer;

    /**
     * Customer recommended product type
     */
    @Schema(description = "Customer Recommended Product Type", example="Rosé")
    private String productType;

    /**
     * Customer recommended products
     */
    @ArraySchema(
            schema = @Schema(
                    implementation = ProductDTO.class,
                    description = " Customer Product Recommendations"
            )
    )
    private ProductDTO[] products;
}
