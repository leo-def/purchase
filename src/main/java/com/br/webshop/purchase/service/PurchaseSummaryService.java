package com.br.webshop.purchase.service;

import com.br.webshop.purchase.exception.purchasesummary.GeneratePurchaseSummaryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.domain.PurchaseSummary;

import java.util.Arrays;

/**
 * Service class responsible for generating purchase summaries.
 */
@Service
public class PurchaseSummaryService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseSummaryService.class);

    /**
     * Generates a summary of the given purchases.
     *
     * @param purchases an array of Purchase objects
     * @return a PurchaseSummary object containing the total count and cost of the purchases
     */
    public PurchaseSummary generateSummary(Purchase[] purchases) {
        try {
            logger.debug("Generating purchase summary for {} purchases", purchases.length);

            int count = purchases.length;
            double totalCost = Arrays.stream(purchases)
                    .mapToDouble(Purchase::getTotalCost)
                    .sum();

            PurchaseSummary summary = PurchaseSummary.builder()
                    .count(count)
                    .totalCost(totalCost)
                    .build();

            logger.debug("Generated purchase summary: {}", summary);
            return summary;
        } catch (Exception ex) {
            throw new GeneratePurchaseSummaryException("Failed to generate purchase summary", ex);
        }
    }
}
