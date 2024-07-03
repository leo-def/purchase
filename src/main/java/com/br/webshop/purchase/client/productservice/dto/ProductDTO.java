package com.br.webshop.purchase.client.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 *  ProductDTO DTO from product source
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    /**
     *  Product code
     */
    @JsonProperty("codigo")
    private String code;

    /**
     *  Product type
     */
    @JsonProperty("tipo_vinho")
    private String type;

    /**
     *  Product price
     */
    @JsonProperty("preco")
    private Double price;

    /**
     *  Product harvest
     */
    @JsonProperty("safra")
    private String harvest;

    /**
     *  Product purchase year
     */
    @JsonProperty("ano_compra")
    private Integer year;
}
