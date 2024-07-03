package com.br.webshop.purchase.controller;

import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.dto.ApiErrorResponseDTO;
import com.br.webshop.purchase.dto.schemas.CustomerDTO;
import com.br.webshop.purchase.dto.schemas.ProductDTO;
import com.br.webshop.purchase.dto.schemas.PurchaseDTO;
import com.br.webshop.purchase.dto.schemas.PurchaseSummaryDTO;
import com.br.webshop.purchase.mapper.CustomerMapper;
import com.br.webshop.purchase.service.CustomerService;
import com.br.webshop.purchase.service.DataCoordinatorService;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebFluxTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DataCoordinatorService dataCoordinatorService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void getMostLoyalCustomers_success() {
        // Arrange
        Customer customer1 = DomainUtils.getCustomer1();
        Customer customer2 = DomainUtils.getCustomer2();
        Customer customer3 = DomainUtils.getCustomer3();
        CustomerDTO customerDTO1 = CustomerDTO
                .builder()
                .name("John Doe")
                .citizenId("999888877733")
                .purchases(new PurchaseDTO[]{
                        PurchaseDTO
                                .builder()
                                .quantity(2)
                                .totalCost(243.0)
                                .product(ProductDTO
                                        .builder()
                                        .code("1")
                                        .type("Tinto")
                                        .price(121.5)
                                        .harvest("2019")
                                        .year(2020)
                                        .build())
                                .build()
                })
                .purchaseSummary(PurchaseSummaryDTO
                        .builder()
                        .count(1)
                        .totalCost(243.0)
                        .build())
                .build();
        CustomerDTO customerDTO2 = CustomerDTO
                .builder()
                .name("Jane Smith")
                .citizenId("199888877733")
                .purchases(new PurchaseDTO[]{
                        PurchaseDTO
                                .builder()
                                .quantity(1)
                                .totalCost(430.3)
                                .product(ProductDTO
                                        .builder()
                                        .code("4")
                                        .type("RosÊ")
                                        .price(430.3)
                                        .harvest("2009")
                                        .year(2020)
                                        .build())
                                .build()
                })
                .purchaseSummary(PurchaseSummaryDTO
                        .builder()
                        .count(1)
                        .totalCost(430.3)
                        .build())
                .build();
        CustomerDTO customerDTO3 = CustomerDTO
                .builder()
                .name("Bob Johnson")
                .citizenId("299888877733")
                .purchases(new PurchaseDTO[]{})
                .purchaseSummary(PurchaseSummaryDTO
                        .builder()
                        .count(0)
                        .totalCost(0d)
                        .build())
                .build();
        Flux<Customer> customerFlux = Flux.just(customer1, customer2, customer3);

        when(dataCoordinatorService.getEnrichedCustomers()).thenReturn(customerFlux);
        when(customerService.getMostLoyalCustomers(customerFlux)).thenReturn(customerFlux);
        when(customerMapper.toDTO(customer1)).thenReturn(customerDTO1);
        when(customerMapper.toDTO(customer2)).thenReturn(customerDTO2);
        when(customerMapper.toDTO(customer3)).thenReturn(customerDTO3);

        // Act & Assert
        webTestClient.get().uri("/clientes-fieis")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDTO.class)
                .hasSize(3)
                .contains(customerDTO1, customerDTO2, customerDTO3);
    }

    @Test
    void getMostLoyalCustomers_failure() {
        // Arrange
        when(dataCoordinatorService.getEnrichedCustomers()).thenThrow(new RuntimeException("Internal Server Error"));

        // Act & Assert
        webTestClient.get().uri("/clientes-fieis")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ApiErrorResponseDTO.class)
                .consumeWith(response -> {
                    ApiErrorResponseDTO errorResponse = response.getResponseBody();
                    assert errorResponse != null;
                    assert "Internal Server Error".equals(errorResponse.getMessage());
                });
    }
}
