package com.br.webshop.purchase.dto.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * API Product DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product Information")
public class ProductDTO {
    /**
     * Product code
     */
    @Schema(description = "Product Code", example = "3")
    private String code;

    /**
     * Product type
     */
    @Schema(description = " Product Type", example = "Rosé")
    private String type;

    /**
     * Product price
     */
    @Schema(description = " Product Price", example = "121.75")
    private Double price;

    /**
     * Product harvest
     */
    @Schema(description = " Product Harvest", example = "2019")
    private String harvest;

    /**
     * Product purchase year
     */
    @Schema(description = " Product Purchase Year", example = "2020")
    private Integer year;
}
