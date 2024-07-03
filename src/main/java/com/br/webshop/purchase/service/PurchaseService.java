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

    /**
     * Sorts purchases by total cost in descending order.
     *
     * @param purchases the Flux of Purchase objects
     * @return Flux of sorted Purchase objects
     */
    public Flux<Purchase> sortByTotalCost(Flux<Purchase> purchases) {
        logger.debug("Processing sort purchase by total cost");
        return purchases.sort(Comparator.comparingDouble(Purchase::getTotalCost).reversed())
                .onErrorMap(throwable -> new SortPurchaseException("Failed to sort purchase by total cost", throwable))
                .doOnNext(purchase -> logger.debug("Processing purchase: {}", purchase))
                .doOnComplete(() -> logger.debug("Completed processing sort purchase by total cost"));
    }

    /**
     * Returns the largest purchase of the specified year.
     *
     * @param year      the year to filter purchases by
     * @param purchases the Flux of Purchase objects
     * @return Mono of the largest Purchase object
     */
    public Mono<Purchase> getYearsLargestPurchase(int year, Flux<Purchase> purchases) {
        logger.debug("Processing get years largest purchase");
        return purchases
                .filter(purchase -> purchase.getProduct().getYear() == year)
                .sort(Comparator.comparingDouble(Purchase::getTotalCost))
                .last()
                .onErrorMap(throwable -> new GetYearsLargestPurchaseException("Failed to get years largest purchase", throwable))
                .doOnNext(purchase -> logger.debug("Processing purchase: {}", purchase))
                .doOnTerminate(() -> logger.debug("Completed processing  get years largest purchase"));

    }

    /**
     * Retrieves all purchases for a Flux of customers.
     *
     * @param customers the Flux of Customer objects
     * @return Flux of Purchase objects
     */
    public Flux<Purchase> getCustomerPurchases(Flux<Customer> customers) {
        logger.debug("Processing get purchases by customers");
        return customers.flatMap(this::getCustomerPurchases)
                .onErrorMap(throwable -> new GetCustomerPurchasesException("Failed to get purchases by customers", throwable))
                .doOnNext(purchase -> logger.debug("Processing purchase: {}", purchase))
                .doOnComplete(() -> logger.debug("Completed processing get purchases by customer"));
    }

    /**
     * Retrieves all purchases for a specific customer.
     *
     * @param customer the Customer object
     * @return Flux of Purchase objects
     */
    private Flux<Purchase> getCustomerPurchases(Customer customer) {
        logger.debug("Processing get purchases by customer: " + customer.getName());
        return Flux.fromArray(customer.getPurchases())
                .onErrorMap(throwable -> new GetCustomerPurchasesException("Failed to get purchases by customer" + customer.getName(), throwable));
    }
}
