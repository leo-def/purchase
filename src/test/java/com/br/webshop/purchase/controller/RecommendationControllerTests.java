package com.br.webshop.purchase.controller;

import com.br.webshop.purchase.domain.*;
import com.br.webshop.purchase.dto.schemas.*;
import com.br.webshop.purchase.mapper.RecommendationMapper;
import com.br.webshop.purchase.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTests {

    @Mock
    private RecommendationService recommendationService;

    @Mock
    private RecommendationMapper recommendationMapper;

    @InjectMocks
    private RecommendationController recommendationController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(recommendationController).build();
    }

    @Test
    public void testGetRecommendation() {
        // Mock data
        Recommendation recommendation1 = Recommendation
                .builder()
                .customer(Customer
                        .builder()
                        .name("John Doe")
                        .citizenId("999888877733")
                        .purchases(new Purchase[]{
                                Purchase
                                        .builder()
                                        .quantity(2)
                                        .totalCost(243.0)
                                        .product(Product
                                                .builder()
                                                .code("1")
                                                .type("Tinto")
                                                .price(121.5)
                                                .harvest("2019")
                                                .year(2020)
                                                .build())
                                        .build()
                        })
                        .purchaseSummary(PurchaseSummary
                                .builder()
                                .count(1)
                                .totalCost(243.0)
                                .build())
                        .build())
                .productType("Tinto")
                .products(new Product[]{
                        Product
                                .builder()
                                .code("1")
                                .type("Tinto")
                                .price(121.5)
                                .harvest("2019")
                                .year(2020)
                                .build(),
                        Product
                                .builder()
                                .code("2")
                                .type("Tinto")
                                .price(140.0)
                                .harvest("2019")
                                .year(2021)
                                .build(),
                        Product
                                .builder()
                                .code("3")
                                .type("Tinto")
                                .price(430.2)
                                .harvest("2007")
                                .year(2019)
                                .build()
                })
                .build();
        RecommendationDTO recommendationDTO1 = RecommendationDTO
                .builder()
                .customer(CustomerDTO
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
                        .build())
                .productType("Tinto")
                .products(new ProductDTO[]{
                        ProductDTO
                                .builder()
                                .code("1")
                                .type("Tinto")
                                .price(121.5)
                                .harvest("2019")
                                .year(2020)
                                .build(),
                        ProductDTO
                                .builder()
                                .code("2")
                                .type("Tinto")
                                .price(140.0)
                                .harvest("2019")
                                .year(2021)
                                .build(),
                        ProductDTO
                                .builder()
                                .code("3")
                                .type("Tinto")
                                .price(430.2)
                                .harvest("2007")
                                .year(2019)
                                .build()
                })
                .build();

        // Mock service responses
        Mockito.when(recommendationService.getRecommendations()).thenReturn(
                (Flux<Recommendation>) Flux.just(recommendation1)
        );
        Mockito.when(recommendationMapper.toDTO(any())).thenReturn(recommendationDTO1);

        // Perform request and verify
        webTestClient.get().uri("/recomendacao/cliente/tipo")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RecommendationDTO.class)
                .value(response -> {
                    RecommendationDTO dto = response.get(0);
                    // Assertions
                    assertThat(recommendation1.getCustomer().getName()).isEqualTo(recommendationDTO1.getCustomer().getName());
                    assertThat(recommendation1.getCustomer().getCitizenId()).isEqualTo(recommendationDTO1.getCustomer().getCitizenId());
                    assertThat(recommendation1.getCustomer().getPurchases().length).isEqualTo(recommendationDTO1.getCustomer().getPurchases().length);
                    assertThat(recommendation1.getCustomer().getPurchaseSummary().getTotalCost()).isEqualTo(recommendationDTO1.getCustomer().getPurchaseSummary().getTotalCost());
                    assertThat(recommendation1.getProductType()).isEqualTo(recommendationDTO1.getProductType());
                    assertThat(recommendation1.getProducts().length).isEqualTo(recommendationDTO1.getProducts().length);
                });
    }
}
