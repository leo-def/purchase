package com.br.webshop.purchase.service;

import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Recommendation;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecommendationServiceTests {

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendations_success() {
        Customer customer1 = DomainUtils.getCustomer4();
        Product product1 = DomainUtils.getProduct1();
        Product product2 = DomainUtils.getProduct2();
        when(productService.getProducts()).thenReturn(Flux.just(product1, product2));
        when(customerService.getEnrichedCustomers(any(Flux.class))).thenReturn(Flux.just(customer1));

        Flux<Recommendation> result = recommendationService.getRecommendations();

        StepVerifier.create(result)
                .expectNextMatches(recommendation -> recommendation.getCustomer().equals(customer1) && recommendation.getProducts().length == 1)
                .verifyComplete();
    }

    @Test
    void testGetRecommendations_emptyProducts() {
        when(productService.getProducts()).thenReturn(Flux.empty());
        when(customerService.getEnrichedCustomers(any(Flux.class))).thenReturn(Flux.empty());

        Flux<Recommendation> result = recommendationService.getRecommendations();

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testGenerateRecommendation_success() {
        Customer customer1 = DomainUtils.getCustomer4();
        Product product1 = DomainUtils.getProduct1();
        Product product2 = DomainUtils.getProduct2();

        when(productService.getProducts()).thenReturn(Flux.just(product1, product2));

        Mono<Recommendation> result = recommendationService.generateRecommendation(Flux.just(product1), customer1);

        StepVerifier.create(result)
                .expectNextMatches(recommendation -> recommendation.getCustomer().equals(customer1) && recommendation.getProducts().length == 1)
                .verifyComplete();
    }
}
