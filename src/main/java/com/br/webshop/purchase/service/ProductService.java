package com.br.webshop.purchase.service;

import com.br.webshop.purchase.exception.product.GetProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.br.webshop.purchase.client.productservice.ProductServiceClient;
import com.br.webshop.purchase.domain.Product;

/**
 * Service class responsible for handling product-related operations.
 */
@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductServiceClient productServiceClient;

    /**
     * Constructs a ProductService with the necessary dependency.
     *
     * @param productServiceClient the ProductServiceClient to fetch products
     */
    @Autowired
    public ProductService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    /**
     * Retrieves all products from the product service client.
     *
     * @return Flux of Product objects
     */
    public Flux<Product> getProducts() {
        logger.debug("Processing get products");
        return productServiceClient.getProducts()
                .onErrorMap(throwable -> new GetProductException("Failed to get product", throwable))
                .doOnNext(product -> logger.debug("Processing product: {}", product))
                .doOnComplete(() -> logger.debug("Completed processing all products"));
    }
}
