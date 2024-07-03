package com.br.webshop.purchase.client.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 *  Purchase DTO from customer source
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    /**
     *  Purchase product code
     */
    @JsonProperty("codigo")
    private String code;

    /**
     *  Purchase product quantity
     */
    @JsonProperty("quantidade")
    private Integer quantity;
}
