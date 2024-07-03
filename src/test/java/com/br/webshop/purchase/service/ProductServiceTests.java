package com.br.webshop.purchase.service;

import com.br.webshop.purchase.client.productservice.ProductServiceClient;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.exception.product.GetProductException;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class ProductServiceTests {

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testGetProducts_success() {
        Product product1 = DomainUtils.getProduct1();
        Product product2 = DomainUtils.getProduct2();

        when(productServiceClient.getProducts()).thenReturn(Flux.just(product1, product2));

        Flux<Product> result = productService.getProducts();

        StepVerifier.create(result)
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void testGetProducts_failure() {
        when(productServiceClient.getProducts()).thenReturn(Flux.error(new RuntimeException("Service unavailable")));

        Flux<Product> result = productService.getProducts();

        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    assert throwable instanceof GetProductException;
                    assert throwable.getMessage().equals("Failed to get product");
                })
                .verify();
    }
}
