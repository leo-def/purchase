package com.br.webshop.purchase.mapper;

import com.br.webshop.purchase.dto.schemas.ProductDTO;
import com.br.webshop.purchase.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductMapperTests {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testFromClientDTO() {
        // Given
        com.br.webshop.purchase.client.productservice.dto.ProductDTO clientDTO =
                com.br.webshop.purchase.client.productservice.dto.ProductDTO
                        .builder()
                        .code("1")
                        .type("Tinto")
                        .price(121.5)
                        .harvest("2019")
                        .year(2020)
                        .build();

        Product expectedProduct = Product
                .builder()
                .code("1")
                .type("Tinto")
                .price(121.5)
                .harvest("2019")
                .year(2020)
                .build();
        when(modelMapper.map(clientDTO, Product.class)).thenReturn(expectedProduct);

        // When
        Product result = productMapper.fromClientDTO(clientDTO);

        // Then
        assertEquals(expectedProduct, result);
    }

    @Test
    public void testToDTO() {
        // Given
        Product product = Product
                .builder()
                .code("1")
                .type("Tinto")
                .price(121.5)
                .harvest("2019")
                .year(2020)
                .build();

        ProductDTO expectedDTO = ProductDTO
                .builder()
                .code("1")
                .type("Tinto")
                .price(121.5)
                .harvest("2019")
                .year(2020)
                .build();

        when(modelMapper.map(product, ProductDTO.class)).thenReturn(expectedDTO);

        // When
        ProductDTO result = productMapper.toDTO(product);

        // Then
        assertEquals(expectedDTO, result);
    }
}
