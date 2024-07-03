package com.br.webshop.purchase.service;

import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.exception.purchase.GetCustomerPurchasesException;
import com.br.webshop.purchase.exception.purchase.GetYearsLargestPurchaseException;
import com.br.webshop.purchase.exception.purchase.SortPurchaseException;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class PurchaseServiceTests {

    @Mock
    private ProductService productService;
    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testEnrichPurchasesWithProductInfo_success() {
        Customer customer = DomainUtils.getNotEnrichedCustomer1();
        Product product = DomainUtils.getProduct1();
        Purchase purchase = customer.getPurchases()[0];

        when(productService.getProducts()).thenReturn(Flux.just(product));

        Flux<Purchase> result = purchaseService.enrichPurchasesWithProductInfo(customer, Flux.just(product));

        StepVerifier.create(result)
                .expectNextMatches(enrichedPurchase -> {
                    assert enrichedPurchase.getProduct().equals(product);
                    assert enrichedPurchase.getTotalCost() == product.getPrice() * purchase.getQuantity();
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testGetCustomerPurchases_success() {
        Customer customer1 = DomainUtils.getCustomer1();
        Customer customer2 = DomainUtils.getCustomer2();

        Flux<Customer> customers = Flux.just(customer1, customer2);

        Flux<Purchase> result = purchaseService.getCustomerPurchases(customers);

        StepVerifier.create(result)
                .expectNext(customer1.getPurchases()[0])
                .expectNext(customer2.getPurchases()[0])
                .verifyComplete();
    }

    @Test
    void testGetCustomerPurchases_failure() {
        Customer customer = DomainUtils.getCustomer1();
        Flux<Customer> customers = Flux.error(new RuntimeException("Get purchase error"));

        Flux<Purchase> result = purchaseService.getCustomerPurchases(customers);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof GetCustomerPurchasesException &&
                        throwable.getMessage().equals("Failed to get purchases by customers"))
                .verify();
    }
}
