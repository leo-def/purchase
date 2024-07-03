package com.br.webshop.purchase.controller;

import com.br.webshop.purchase.mapper.PurchaseMapper;
import com.br.webshop.purchase.service.DataCoordinatorService;
import com.br.webshop.purchase.service.PurchaseService;
import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.dto.ApiErrorResponseDTO;
import com.br.webshop.purchase.dto.schemas.PurchaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing purchases.
 */
@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchase", description = "Purchase service endpoints")
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final DataCoordinatorService dataCoordinatorService;
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    /**
     * Constructs a PurchaseController with the necessary dependencies.
     *
     * @param dataCoordinatorService the DataCoordinatorService for coordinating data
     * @param purchaseService        the PurchaseService for purchase-related operations
     * @param purchaseMapper         the PurchaseMapper for mapping Purchase objects to DTOs
     */
    @Autowired
    public PurchaseController(
            DataCoordinatorService dataCoordinatorService,
            PurchaseService purchaseService,
            PurchaseMapper purchaseMapper
    ) {
        this.dataCoordinatorService = dataCoordinatorService;
        this.purchaseService = purchaseService;
        this.purchaseMapper = purchaseMapper;
    }

    /**
     * Retrieves all purchases sorted by total cost.
     *
     * @return a {@link Flux} of {@link PurchaseDTO} representing the sorted purchases.
     */
    @Operation(
            summary = "Get Purchases Sorted by Total Cost",
            description = "Return a list of all purchases sorted by total cost in descending order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @GetMapping("/compras")
    public Flux<PurchaseDTO> getPurchasesSortedByTotalCost() {
        logger.info("Received request to getPurchasesSortedByTotalCost");
        Flux<Purchase> purchases = dataCoordinatorService.getEnrichedCustomerPurchases();
        return purchaseService.sortByTotalCost(purchases)
                .map(purchaseMapper::toDTO)
                .doOnNext(response -> logger.info("Emitting response: {}", response))
                .doOnComplete(() -> logger.info("Completed emitting responses"));
    }

    /**
     * Retrieves the largest purchase of the specified year.
     *
     * @param year the year to filter purchases.
     * @return a {@link Mono} of {@link PurchaseDTO} representing the largest purchase of the year.
     */
    @Operation(
            summary = "Get Year's Largest Purchase",
            description = "Return the largest purchase of the specified year with the provided purchase details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @GetMapping("/maior-compra/{year}")
    public Mono<PurchaseDTO> getYearsLargestPurchase(@PathVariable Integer year) {
        logger.info("Received request to getYearsLargestPurchase for year: {}", year);
        Flux<Purchase> purchases = dataCoordinatorService.getEnrichedCustomerPurchases();
        return purchaseService.getYearsLargestPurchase(year, purchases)
                .map(purchaseMapper::toDTO)
                .doOnNext(response -> logger.info("Emitting response: {}", response))
                .doOnTerminate(() -> logger.info("Completed emitting responses"));
    }
}
