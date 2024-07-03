package com.br.webshop.purchase.service;

import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class responsible for coordinating data retrieval and enrichment operations.
 */
@Service
public class DataCoordinatorService {

    private final PurchaseService purchaseService;
    private final CustomerService customerService;
    private final ProductService productService;

    /**
     * Constructs a DataCoordinatorService with the necessary dependencies.
     *
     * @param purchaseService the PurchaseService to handle purchase-related operations
     * @param customerService the CustomerService to handle customer-related operations
     * @param productService  the ProductService to handle product-related operations
     */
    @Autowired
    public DataCoordinatorService(PurchaseService purchaseService, CustomerService customerService, ProductService productService) {
        this.purchaseService = purchaseService;
        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Retrieves enriched customers with product information.
     *
     * @return Flux of enriched Customer objects
     */
    public Flux<Customer> getEnrichedCustomers() {
        Flux<Product> products = productService.getProducts();
        return customerService.getEnrichedCustomers(products);
    }

    /**
     * Retrieves enriched customer purchases with product information.
     *
     * @return Flux of enriched Purchase objects
     */
    public Flux<Purchase> getEnrichedCustomerPurchases() {
        Flux<Customer> enrichedCustomers = getEnrichedCustomers();
        return purchaseService.getCustomerPurchases(enrichedCustomers);
    }
}