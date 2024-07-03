package com.br.webshop.purchase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Customer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    /**
     *  Customer full name
     */
    private String name;

    /**
     *  Customer citizen id (CPF)
     */
    private String citizenId;

    /**
     *  Customer purchases
     */
    private Purchase[] purchases;

    /**
     *  Customer purchases summary info
     */
    private PurchaseSummary purchaseSummary;
}
