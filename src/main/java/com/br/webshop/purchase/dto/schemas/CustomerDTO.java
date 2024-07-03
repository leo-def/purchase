package com.br.webshop.purchase.dto.schemas;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * API Customer DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer Information")
public class CustomerDTO {
    /**
     * Customer name
     */
    @Schema(description = " Customer Name", example = "Pietra Oliveira Silva")
    private String name;

    /**
     * Customer citizen id (CPF)
     */
    @Schema(description = " Customer Citizen Id (CPF)", example = "24102668511")
    private String citizenId;

    /**
     * Customer purchases
     */
    @ArraySchema(
            schema = @Schema(
                    implementation = PurchaseDTO.class,
                    description = "Customer Purchases"
            )
    )
    private PurchaseDTO[] purchases;

    /**
     * Customer purchase summary
     */
    @Schema(description = "Purchases Summary Information")
    private PurchaseSummaryDTO purchaseSummary;

}
