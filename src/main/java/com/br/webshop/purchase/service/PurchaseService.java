package com.br.webshop.purchase.service;

import java.util.Comparator;

import com.br.webshop.purchase.exception.purchase.EnrichPurchaseException;
import com.br.webshop.purchase.exception.purchase.GetCustomerPurchasesException;
import com.br.webshop.purchase.exception.purchase.GetYearsLargestPurchaseException;
import com.br.webshop.purchase.exception.purchase.SortPurchaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Purchase;

/**
 * Service class responsible for handling purchase-related operations.
 */
@Service
public class PurchaseService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    /**
     * Enriches a customer's purchases with product information.
     *
     * @param customer the Customer whose purchases are to be enriched
     * @param products the Flux of Product objects
     * @return Flux of enriched Purchase objects
     */
    public Flux<Purchase> enrichPurchasesWithProductInfo(Customer customer, Flux<Product> products) {
        logger.debug("Processing enrich purchase with product info");
        return Flux.fromArray(customer.getPurchases())
                .flatMap(purchase -> this.enrichPurchaseWithProductInfo(purchase, products))
                .onErrorMap(throwable -> new EnrichPurchaseException("Failed to enrich purchase with product info", throwable))
                .doOnNext(purchase -> logger.debug("Processing enriched purchase: {}", purchase))
                .doOnComplete(() -> logger.debug("Completed processing enrich purchase with product info"));
    }

    /**
     * Enriches a purchase with product information.
     *
     * @param purchase the Purchase to be enriched
     * @param products the Flux of Product objects
     * @return Flux of enriched Purchase objects
     */
    private Flux<Purchase> enrichPurchaseWithProductInfo(Purchase purchase, Flux<Product> products) {
        return products
                .filter(product -> product.getCode().equals(purchase.getCode()))
                .map(product -> enrichPurchaseWithProductDetails(purchase, product));
    }

    /**
     * Sets product details on a purchase.
     *
     * @param purchase the Purchase to be enriched
     * @param product  the Product details to set on the purchase
     * @return the enriched Purchase object
     */
    private Purchase enrichPurchaseWithProductDetails(Purchase purchase, Product product) {
        purchase.setProduct(product);
        purchase.setTotalCost(product.getPrice() * purchase.getQuantity());
        return purchase;
    }
}
