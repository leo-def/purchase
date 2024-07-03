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
}
