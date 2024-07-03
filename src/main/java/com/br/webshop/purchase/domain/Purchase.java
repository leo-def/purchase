package com.br.webshop.purchase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Purchase
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    /**
     *  Purchase product code
     */
    private String code;

    /**
     *  Purchase product quantity
     */
    private Integer quantity;

    /**
     *  Total cost of purchase
     */
    private Double totalCost;

    /**
     *  Purchase product
     */
    private Product product;
}
