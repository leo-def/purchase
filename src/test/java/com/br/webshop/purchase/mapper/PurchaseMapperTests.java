package com.br.webshop.purchase.mapper;

import com.br.webshop.purchase.domain.Product;
import com.br.webshop.purchase.dto.schemas.ProductDTO;
import com.br.webshop.purchase.dto.schemas.PurchaseDTO;
import com.br.webshop.purchase.domain.Purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PurchaseMapperTests {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PurchaseMapper purchaseMapper;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testToDTO() {
        // Given
        Purchase purchase = Purchase
                .builder()
                .code("4")
                .quantity(1)
                .totalCost(430.3)
                .product(Product
                        .builder()
                        .code("4")
                        .type("RosÊ")
                        .price(430.3)
                        .harvest("2009")
                        .year(2020)
                        .build())
                .build();

        PurchaseDTO expectedDTO = PurchaseDTO
                .builder()
                .quantity(1)
                .totalCost(430.3)
                .product(ProductDTO
                        .builder()
                        .code("4")
                        .type("RosÊ")
                        .price(430.3)
                        .harvest("2009")
                        .year(2020)
                        .build())
                .build();

        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedDTO);

        // When
        PurchaseDTO result = purchaseMapper.toDTO(purchase);

        // Then
        assertEquals(expectedDTO, result);
    }
}
