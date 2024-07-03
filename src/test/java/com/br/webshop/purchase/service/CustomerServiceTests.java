package com.br.webshop.purchase.service;

import com.br.webshop.purchase.client.customerservice.CustomerServiceClient;
import com.br.webshop.purchase.client.customerservice.exception.CustomerRetrievalException;
import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.domain.PurchaseSummary;
import com.br.webshop.purchase.exception.customer.EnrichCustomerException;
import com.br.webshop.purchase.exception.customer.GetCustomerException;
import com.br.webshop.purchase.exception.customer.GetMostLoyalCustomerException;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTests {

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchaseSummaryService purchaseSummaryService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testGetAllCustomers() {
        Customer customer = DomainUtils.getCustomer1();
        when(customerServiceClient.getCustomers()).thenReturn(Flux.just(customer));

        Flux<Customer> result = customerService.getAllCustomers();

        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        verify(customerServiceClient, times(1)).getCustomers();
    }

    @Test
    void testGetAllCustomers_withException() {
        when(customerServiceClient.getCustomers()).thenReturn(Flux.error(new CustomerRetrievalException("Error")));

        Flux<Customer> result = customerService.getAllCustomers();

        StepVerifier.create(result)
                .expectError(GetCustomerException.class)
                .verify();

        verify(customerServiceClient, times(1)).getCustomers();
    }

    @Test
    void testGetEnrichedCustomers() {
        Customer customer = DomainUtils.getCustomer1();
        Product product = DomainUtils.getProduct1();
        Purchase purchase = DomainUtils.getPurchase1();
        when(customerServiceClient.getCustomers()).thenReturn(Flux.just(customer));
        when(purchaseService.enrichPurchasesWithProductInfo(any(Customer.class), any(Flux.class))).thenReturn(Flux.just(purchase));
        when(purchaseSummaryService.generateSummary(any(Purchase[].class))).thenReturn(new PurchaseSummary());

        Flux<Customer> result = customerService.getEnrichedCustomers(Flux.just(product));

        StepVerifier.create(result)
                .expectNextMatches(enrichedCustomer -> enrichedCustomer.getPurchases().length == 1)
                .verifyComplete();

        verify(customerServiceClient, times(1)).getCustomers();
        verify(purchaseService, times(1)).enrichPurchasesWithProductInfo(any(Customer.class), any(Flux.class));
        verify(purchaseSummaryService, times(1)).generateSummary(any(Purchase[].class));
    }

    @Test
    void testGetEnrichedCustomers_withException() {
        Customer customer = DomainUtils.getCustomer1();
        when(customerServiceClient.getCustomers()).thenReturn(Flux.just(customer));
        when(purchaseService.enrichPurchasesWithProductInfo(any(Customer.class), any(Flux.class))).thenReturn(Flux.error(new RuntimeException("Error")));

        Flux<Customer> result = customerService.getEnrichedCustomers(Flux.empty());

        StepVerifier.create(result)
                .expectError(EnrichCustomerException.class)
                .verify();

        verify(customerServiceClient, times(1)).getCustomers();
        verify(purchaseService, times(1)).enrichPurchasesWithProductInfo(any(Customer.class), any(Flux.class));
    }
}
