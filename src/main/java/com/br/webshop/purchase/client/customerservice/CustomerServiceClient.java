package com.br.webshop.purchase.client.customerservice;

import com.br.webshop.purchase.client.customerservice.dto.CustomerDTO;
import com.br.webshop.purchase.client.customerservice.exception.CustomerRetrievalException;
import com.br.webshop.purchase.mapper.CustomerMapper;
import com.br.webshop.purchase.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Service client for retrieving customer data from an external service.
 */
@Service
public class CustomerServiceClient {

    private final CustomerMapper customerMapper;
    private final WebClient customerWebClient;
    private final String getCustomersPath;

    /**
     * Constructs a CustomerServiceClient with the necessary dependencies.
     *
     * @param customerMapper    the CustomerMapper for mapping CustomerDTO to Customer
     * @param customerWebClient the WebClient for making HTTP requests
     * @param getCustomersPath  the URI path for retrieving customers
     */
    @Autowired
    public CustomerServiceClient(CustomerMapper customerMapper,
                                 @Qualifier("customerWebClient") WebClient customerWebClient,
                                 @Value("${webclient.customer-service.get-customers-path}") String getCustomersPath) {
        this.customerMapper = customerMapper;
        this.customerWebClient = customerWebClient;
        this.getCustomersPath = getCustomersPath;
    }

    /**
     * Retrieves a list of customers from the external service.
     *
     * @return a Flux of Customer objects
     */
    public Flux<Customer> getCustomers() {
        return this.customerWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(getCustomersPath).build())
                .retrieve()
                .bodyToMono(CustomerDTO[].class)
                .flatMapMany(Flux::fromArray)
                .map(customerMapper::fromClientDTO)
                .onErrorMap(throwable -> new CustomerRetrievalException("Failed to retrieve customers", throwable));
    }
}
