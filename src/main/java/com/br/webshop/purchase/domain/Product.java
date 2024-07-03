package com.br.webshop.purchase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Product
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /**
     *  Product code
     */
    private String code;

    /**
     *  Product type
     */
    private String type;

    /**
     *  Product price
     */
    private Double price;

    /**
     *  Product harvest
     */
    private String harvest;

    /**
     *  Product purchase year
     */
    private Integer year;
}
