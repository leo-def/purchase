package com.br.webshop.purchase.service;

import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.exception.customer.EnrichCustomerException;
import com.br.webshop.purchase.exception.customer.GetCustomerException;
import com.br.webshop.purchase.exception.customer.GetMostLoyalCustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.br.webshop.purchase.client.customerservice.CustomerServiceClient;
import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;

import java.util.Comparator;

/**
 * Service class for handling customer-related operations.
 */
@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerServiceClient customerServiceClient;

    private final PurchaseService purchaseService;

    private final PurchaseSummaryService purchaseSummaryService;

    /**
     * Constructs a CustomerService with the necessary dependencies.
     *
     * @param customerServiceClient  the CustomerServiceClient to handle requests to client source
     * @param purchaseService        the PurchaseService to handle purchase-related operations
     * @param purchaseSummaryService the PurchaseSummaryService to handle purchase-summary-related operations
     */
    @Autowired
    public CustomerService(
            CustomerServiceClient customerServiceClient,
            PurchaseService purchaseService,
            PurchaseSummaryService purchaseSummaryService
    ) {
        this.customerServiceClient = customerServiceClient;
        this.purchaseService = purchaseService;
        this.purchaseSummaryService = purchaseSummaryService;
    }

    /**
     * Retrieves all customers.
     *
     * @return Flux of Customer objects
     */
    Flux<Customer> getAllCustomers() {
        logger.debug("Processing get all customers");
        return customerServiceClient.getCustomers()
                .onErrorMap(throwable -> new GetCustomerException("Failed to get customer", throwable))
                .doOnNext(result -> logger.debug("Processed getAllCustomers: {}", result))
                .doOnComplete(() -> logger.debug("Completed processing getAllCustomers"));
    }

    /**
     * Retrieves enriched customers with product information.
     *
     * @param products Flux of Product objects
     * @return Flux of enriched Customer objects
     */
    public Flux<Customer> getEnrichedCustomers(Flux<Product> products) {
        return getAllCustomers()
                .flatMap(customer -> enrichCustomerWithProductInfo(customer, products));
    }

    /**
     * Enriches a customer with product information.
     *
     * @param customer Customer object to enrich
     * @param products Flux of Product objects
     * @return Mono of enriched Customer object
     */
    private Mono<Customer> enrichCustomerWithProductInfo(Customer customer, Flux<Product> products) {
        logger.debug("Processing enrich customer with product info");
        return purchaseService.enrichPurchasesWithProductInfo(customer, products)
                .collectList()
                .map(purchases -> {
                    Purchase[] purchaseArray = purchases.toArray(new Purchase[0]);
                    customer.setPurchases(purchaseArray);
                    customer.setPurchaseSummary(purchaseSummaryService.generateSummary(purchaseArray));
                    return customer;
                })
                .onErrorMap(throwable -> new EnrichCustomerException("Failed to enrich customer with product info", throwable))
                .doOnNext(result -> logger.debug("Processed enrichCustomerWithProductInfo: {}", result))
                .doOnTerminate(() -> logger.debug("Completed processing enrichCustomerWithProductInfo"));
    }

    /**
     * Retrieves the top 3 most loyal customers based on their purchase history.
     *
     * @param customers Flux of Customer objects
     * @return Flux of the top 3 most loyal Customer objects
     */
    public Flux<Customer> getMostLoyalCustomers(Flux<Customer> customers) {
        logger.debug("Processing most loyal customers");
        return customers
                .sort(Comparator
                        .comparingInt((Customer customer) -> customer.getPurchaseSummary().getCount())
                        .thenComparingDouble((Customer customer) -> customer.getPurchaseSummary().getTotalCost())
                        .reversed())
                .take(3)
                .onErrorMap(throwable -> new GetMostLoyalCustomerException("Failed to get most loyal customers", throwable))
                .doOnNext(result -> logger.debug("Processed getMostLoyalCustomers: {}", result))
                .doOnComplete(() -> logger.debug("Completed processing getMostLoyalCustomers"));
    }
}
