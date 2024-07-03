package com.br.webshop.purchase.controller;

import com.br.webshop.purchase.mapper.CustomerMapper;
import com.br.webshop.purchase.service.CustomerService;
import com.br.webshop.purchase.service.DataCoordinatorService;
import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.dto.ApiErrorResponseDTO;
import com.br.webshop.purchase.dto.schemas.CustomerDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping()
@Tag(name = "Customer", description = "Customer Management Endpoints")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final DataCoordinatorService dataCoordinatorService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    /**
     * Constructs a CustomerController with the necessary dependencies.
     *
     * @param dataCoordinatorService the DataCoordinatorService for coordinating data
     * @param customerService        the CustomerService for customer-related operations
     * @param customerMapper         the CustomerMapper for mapping Customer objects to DTOs
     */
    @Autowired
    public CustomerController(
            DataCoordinatorService dataCoordinatorService,
            CustomerService customerService,
            CustomerMapper customerMapper
    ) {
        this.dataCoordinatorService = dataCoordinatorService;
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    /**
     * Retrieves the top 3 most loyal customers.
     *
     * @return a {@link Flux} of {@link CustomerDTO} representing the most loyal customers.
     */
    @Operation(
            summary = "Get Most Loyal Customers",
            description = "Return the top 3 most loyal customers, who have the highest number of recurring purchases with the highest values."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @GetMapping("/clientes-fieis")
    public Flux<CustomerDTO> getMostLoyalCustomers() {
        logger.info("Received request to getMostLoyalCustomers");
        Flux<Customer> customers = dataCoordinatorService.getEnrichedCustomers();
        return customerService.getMostLoyalCustomers(customers)
                .map(customerMapper::toDTO)
                .doOnNext(response -> logger.info("Emitting response: {}", response))
                .doOnComplete(() -> logger.info("Completed emitting responses"));
    }
}
