package com.br.webshop.purchase.client.productservice;

import com.br.webshop.purchase.client.productservice.dto.ProductDTO;
import com.br.webshop.purchase.client.productservice.exception.ProductRetrievalException;
import com.br.webshop.purchase.mapper.ProductMapper;
import com.br.webshop.purchase.domain.Product;
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

class ProductServiceClientTests {

    @Mock
    private ProductMapper productMapper;

    @Mock(name = "productWebClient")
    private WebClient productWebClient;

    @InjectMocks
    private ProductServiceClient productServiceClient;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void getProducts_success() {
        ProductDTO[] productDTOArray = {
                ProductDTO
                        .builder()
                        .code("1")
                        .type("Tinto")
                        .price(121.5)
                        .harvest("2019")
                        .year(2020)
                        .build()
              };
        Product product = DomainUtils.getProduct1();

        WebClient.RequestHeadersUriSpec<?> uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);


        when(productWebClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) uriSpecMock);
        when(uriSpecMock.uri(any(Function.class))).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ProductDTO[].class)).thenReturn(Mono.just(productDTOArray));
        when(productMapper.fromClientDTO(any(ProductDTO.class))).thenReturn(product);


        // Act & Assert
        Flux<Product> productFlux = productServiceClient.getProducts();

        StepVerifier.create(productFlux)
                .expectNext(product)
                .verifyComplete();

        verify(productWebClient).get();
        verify(headersSpecMock).retrieve();
        verify(responseSpecMock).bodyToMono(ProductDTO[].class);
        verify(productMapper).fromClientDTO(productDTOArray[0]);
    }

    @Test
    void getProducts_failure() {
        // Arrange
        WebClient.RequestHeadersUriSpec<?> uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(productWebClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) uriSpecMock);
        when(uriSpecMock.uri(any(Function.class))).thenReturn((WebClient.RequestHeadersSpec) headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ProductDTO[].class)).thenReturn(Mono.error(new WebClientResponseException(500, "Internal Server Error", null, null, null)));

        Flux<Product> productFlux = productServiceClient.getProducts();

        StepVerifier.create(productFlux)
                .expectError(ProductRetrievalException.class)
                .verify();

        verify(productWebClient).get();
        verify(uriSpecMock).uri(any(Function.class));
        verify(headersSpecMock).retrieve();
        verify(responseSpecMock).bodyToMono(ProductDTO[].class);
    }
}
