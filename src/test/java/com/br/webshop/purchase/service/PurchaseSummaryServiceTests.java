package com.br.webshop.purchase.service;

import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.domain.PurchaseSummary;
import com.br.webshop.purchase.exception.purchasesummary.GeneratePurchaseSummaryException;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseSummaryServiceTests {

    @InjectMocks
    private PurchaseSummaryService purchaseSummaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateSummary_success() {
        Purchase purchase1 = DomainUtils.getPurchase1();
        Purchase purchase2 = DomainUtils.getPurchase2();
        Purchase[] purchases = {purchase1, purchase2};

        PurchaseSummary summary = purchaseSummaryService.generateSummary(purchases);

        assertEquals(2, summary.getCount());
        assertEquals(673.3, summary.getTotalCost(), 0.01);
    }

    @Test
    void testGenerateSummary_emptyPurchases() {
        Purchase[] purchases = {};

        PurchaseSummary summary = purchaseSummaryService.generateSummary(purchases);

        assertEquals(0, summary.getCount());
        assertEquals(0.0, summary.getTotalCost(), 0.01);
    }

    @Test
    void testGenerateSummary_nullPurchases() {
        Exception exception = assertThrows(GeneratePurchaseSummaryException.class, () -> {
            purchaseSummaryService.generateSummary(null);
        });

        String expectedMessage = "Failed to generate purchase summary";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGenerateSummary_throwsException() {
        Purchase[] purchases = {null};

        Exception exception = assertThrows(GeneratePurchaseSummaryException.class, () -> {
            purchaseSummaryService.generateSummary(purchases);
        });

        String expectedMessage = "Failed to generate purchase summary";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
