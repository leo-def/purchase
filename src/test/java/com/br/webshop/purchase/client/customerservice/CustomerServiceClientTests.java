package com.br.webshop.purchase.client.customerservice;

import com.br.webshop.purchase.client.customerservice.dto.CustomerDTO;
import com.br.webshop.purchase.client.customerservice.dto.PurchaseDTO;
import com.br.webshop.purchase.client.customerservice.exception.CustomerRetrievalException;
import com.br.webshop.purchase.mapper.CustomerMapper;
import com.br.webshop.purchase.domain.Customer;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.Mockito.*;

class CustomerServiceClientTests {

    @Mock
    private CustomerMapper customerMapper;

    @Mock(name = "customerWebClient")
    private WebClient customerWebClient;

    @InjectMocks
    private CustomerServiceClient customerServiceClient;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void getCustomers_success() {
        CustomerDTO[] customerDTOArray = {
                CustomerDTO
                        .builder()
                        .name("John Doe")
                        .citizenId("999888877733")
                        .purchases(new PurchaseDTO[]{
                                PurchaseDTO
                                        .builder()
                                        .code("4")
                                        .quantity(1)
                                        .build()
                        })
                        .build()
        };
        Customer customer = DomainUtils.getNotEnrichedCustomer1();

        WebClient.RequestHeadersUriSpec<?> uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(customerWebClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) uriSpecMock);
        when(uriSpecMock.uri(any(Function.class))).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CustomerDTO[].class)).thenReturn(Mono.just(customerDTOArray));
        when(customerMapper.fromClientDTO(any(CustomerDTO.class))).thenReturn(customer);

        Flux<Customer> customerFlux = customerServiceClient.getCustomers();

        StepVerifier.create(customerFlux)
                .expectNext(customer)
                .verifyComplete();

        verify(customerWebClient).get();
        verify(headersSpecMock).retrieve();
        verify(responseSpecMock).bodyToMono(CustomerDTO[].class);
        verify(customerMapper).fromClientDTO(customerDTOArray[0]);
    }

    @Test
    void getCustomers_failure() {
        WebClient.RequestHeadersUriSpec<?> uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(customerWebClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) uriSpecMock);
        when(uriSpecMock.uri(any(Function.class))).thenReturn((WebClient.RequestHeadersSpec) headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CustomerDTO[].class)).thenReturn(Mono.error(new WebClientResponseException(500, "Internal Server Error", null, null, null)));

        Flux<Customer> customerFlux = customerServiceClient.getCustomers();

        StepVerifier.create(customerFlux)
                .expectError(CustomerRetrievalException.class)
                .verify();

        verify(customerWebClient).get();
        verify(uriSpecMock).uri(any(Function.class));
        verify(headersSpecMock).retrieve();
        verify(responseSpecMock).bodyToMono(CustomerDTO[].class);
    }
}
