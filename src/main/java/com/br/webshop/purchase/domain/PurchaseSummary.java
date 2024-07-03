package com.br.webshop.purchase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Summary information for purchases
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSummary {
    /**
     *  Purchases count
     */
    Integer count;

    /**
     *  Purchases total cost amount
     */
    Double totalCost;
}
