package com.br.webshop.purchase.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating WebClient instances for interacting with external services.
 */
@Configuration
public class WebClientConfig {

    @Value("${webclient.product-service.base-url}")
    private String productServiceBaseUrl;

    @Value("${webclient.customer-service.base-url}")
    private String customerServiceBaseUrl;

    /**
     * Provides custom ExchangeStrategies for WebClient instances.
     *
     * @return The configured ExchangeStrategies.
     */
    private ExchangeStrategies getExchangeStrategies() {
        return ExchangeStrategies.builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs()
                            .jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs()
                            .jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
                })
                .build();
    }

    /**
     * Creates a WebClient bean for interacting with the product service.
     *
     * @return The configured WebClient for the product service.
     */
    @Bean("productWebClient")
    public WebClient getProductWebClient() {
        ExchangeStrategies exchangeStrategies = this.getExchangeStrategies();
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(this.productServiceBaseUrl)
                .build();
    }

    /**
     * Creates a WebClient bean for interacting with the customer service.
     *
     * @return The configured WebClient for the customer service.
     */
    @Bean("customerWebClient")
    public WebClient getCustomerWebClient() {
        ExchangeStrategies exchangeStrategies = this.getExchangeStrategies();
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(this.customerServiceBaseUrl)
                .build();
    }
}
