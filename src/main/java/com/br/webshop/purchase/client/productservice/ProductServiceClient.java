package com.br.webshop.purchase.client.productservice;

import com.br.webshop.purchase.client.productservice.dto.ProductDTO;
import com.br.webshop.purchase.client.productservice.exception.ProductRetrievalException;
import com.br.webshop.purchase.mapper.ProductMapper;
import com.br.webshop.purchase.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Service client for retrieving product data from an external service.
 */
@Service
public class ProductServiceClient {

    private final ProductMapper productMapper;
    private final WebClient productWebClient;
    private final String getProductsPath;

    /**
     * Constructs a ProductServiceClient with the necessary dependencies.
     *
     * @param productMapper    the ProductMapper for mapping ProductDTO to Product
     * @param productWebClient the WebClient for making HTTP requests
     * @param getProductsPath  the URI path for retrieving products
     */
    @Autowired
    public ProductServiceClient(ProductMapper productMapper,
                                @Qualifier("productWebClient") WebClient productWebClient,
                                @Value("${webclient.product-service.get-products-path}") String getProductsPath) {
        this.productMapper = productMapper;
        this.productWebClient = productWebClient;
        this.getProductsPath = getProductsPath;
    }

    /**
     * Retrieves a list of products from the external service.
     *
     * @return a Flux of Product objects
     */
    public Flux<Product> getProducts() {
        return this.productWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(this.getProductsPath).build())
                .retrieve()
                .bodyToMono(ProductDTO[].class)
                .flatMapMany(Flux::fromArray)
                .map(productMapper::fromClientDTO)
                .onErrorMap(throwable -> new ProductRetrievalException("Failed to retrieve products", throwable));
    }
}
