package com.br.webshop.purchase.service;

import com.br.webshop.purchase.exception.recommendation.GenerateRecommendationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.domain.Recommendation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for generating product recommendations.
 */
@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final CustomerService customerService;
    private final ProductService productService;

    /**
     * Constructs a RecommendationService with the necessary dependencies.
     *
     * @param customerService the CustomerService to fetch customer data
     * @param productService  the ProductService to fetch product data
     */
    @Autowired
    public RecommendationService(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Generates a product recommendation for a customer based on their purchase history.
     *
     * @param products the Flux of Product objects
     * @param customer the Customer for whom the recommendation is generated
     * @return Mono of Recommendation object
     */
    Mono<Recommendation> generateRecommendation(Flux<Product> products, Customer customer) {
        return Flux.fromArray(customer.getPurchases())
                .collect(HashMap<String, Integer>::new, (map, purchase) -> {
                    String type = purchase.getProduct().getType();
                    map.put(type, map.getOrDefault(type, 0) + purchase.getQuantity());
                })
                .flatMapMany(map -> Flux.fromIterable(
                        map.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .collect(Collectors.toList())
                ))
                .next()
                .map(Map.Entry::getKey)
                .flatMap(recommendedType -> generateRecommendation(products, customer, recommendedType));
    }

    /**
     * Generates a product recommendation for a customer based on a specific product type.
     *
     * @param products    the Flux of Product objects
     * @param customer    the Customer for whom the recommendation is generated
     * @param productType the type of product to base the recommendation on
     * @return Mono of Recommendation object
     */
    private Mono<Recommendation> generateRecommendation(Flux<Product> products, Customer customer, String productType) {
        return products.filter(product -> product.getType().equals(productType))
                .collectList()
                .map(list -> list.toArray(new Product[0]))
                .map(recommendationProducts -> Recommendation.builder()
                        .products(recommendationProducts)
                        .customer(customer)
                        .productType(productType)
                        .build());
    }

    /**
     * Returns product recommendations based on the types of products customers buy the most.
     *
     * @return Flux of Recommendation objects
     */
    public Flux<Recommendation> getRecommendations() {
        logger.debug("Processing recommendations");
        Flux<Product> products = productService.getProducts();
        return customerService.getEnrichedCustomers(products)
                .flatMap(customer -> this.generateRecommendation(products, customer))
                .onErrorMap(throwable -> new GenerateRecommendationException("Failed to generate recommendation", throwable))
                .doOnNext(result -> logger.debug("Generated recommendation: {}", result))
                .doOnComplete(() -> logger.debug("Completed processing recommendations"));
    }
}
