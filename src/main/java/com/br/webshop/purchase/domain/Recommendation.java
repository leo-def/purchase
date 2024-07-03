package com.br.webshop.purchase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Recommendation of products for Customer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    /**
     * Recommendation customer
     */
    private Customer customer;

    /**
     * Recommended product type
     */
    private String productType;

    /**
     * Recommended products
     */
    private Product[] products;
}
