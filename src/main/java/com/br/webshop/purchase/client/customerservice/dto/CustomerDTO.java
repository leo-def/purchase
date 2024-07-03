package com.br.webshop.purchase.client.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 *  Customer DTO from customer source
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    /**
     *  Customer full name
     */
    @JsonProperty("nome")
    private String name;

    /**
     *  Customer citizen id (CPF)
     */
    @JsonProperty("cpf")
    private String citizenId;


    /**
     *  Customer purchases
     */
    @JsonProperty("compras")
    private PurchaseDTO[] purchases;
}
