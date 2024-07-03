package com.br.webshop.purchase.service;

import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DataCoordinatorServiceTests {

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private DataCoordinatorService dataCoordinatorService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testGetEnrichedCustomers() {
        Product product1 = DomainUtils.getProduct1();
        Product product2 = DomainUtils.getProduct2();
        Customer customer1 = DomainUtils.getNotEnrichedCustomer1();
        Customer customer2 = DomainUtils.getNotEnrichedCustomer2();

        when(productService.getProducts()).thenReturn(Flux.just(product1, product2));
        when(customerService.getEnrichedCustomers(any(Flux.class)))
                .thenReturn(Flux.just(customer1, customer2));

        Flux<Customer> result = dataCoordinatorService.getEnrichedCustomers();

        StepVerifier.create(result)
                .expectNext(customer1)
                .expectNext(customer2)
                .verifyComplete();
    }

    @Test
    void testGetEnrichedCustomerPurchases() {
        Product product1 = DomainUtils.getProduct1();
        Product product2 = DomainUtils.getProduct2();
        Customer customer1 = DomainUtils.getNotEnrichedCustomer1();
        Customer customer2 = DomainUtils.getNotEnrichedCustomer2();
        Purchase purchase1 = DomainUtils.getNotEnrichedPurchase1();
        Purchase purchase2 = DomainUtils.getNotEnrichedPurchase2();

        when(productService.getProducts()).thenReturn(Flux.just(product1, product2));
        when(customerService.getEnrichedCustomers(any(Flux.class)))
                .thenReturn(Flux.just(customer1, customer2));
        when(purchaseService.getCustomerPurchases(any(Flux.class)))
                .thenReturn(Flux.just(purchase1, purchase2));

        Flux<Purchase> result = dataCoordinatorService.getEnrichedCustomerPurchases();

        StepVerifier.create(result)
                .expectNext(purchase1)
                .expectNext(purchase2)
                .verifyComplete();
    }
}
