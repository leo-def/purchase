package com.br.webshop.purchase.mapper;


import com.br.webshop.purchase.domain.*;
import com.br.webshop.purchase.dto.schemas.*;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class RecommendationMapperTests {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RecommendationMapper recommendationMapper;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testToDTO() {
        // Given
        Recommendation recommendation = DomainUtils.getRecommendation1();

        RecommendationDTO expectedDTO = RecommendationDTO
                .builder()
                .customer(CustomerDTO
                        .builder()
                        .name("John Doe")
                        .citizenId("999888877733")
                        .purchases(new PurchaseDTO[]{
                                PurchaseDTO
                                        .builder()
                                        .quantity(2)
                                        .totalCost(243.0)
                                        .product(ProductDTO
                                                .builder()
                                                .code("1")
                                                .type("Tinto")
                                                .price(121.5)
                                                .harvest("2019")
                                                .year(2020)
                                                .build())
                                        .build()
                        })
                        .purchaseSummary(PurchaseSummaryDTO
                                .builder()
                                .count(1)
                                .totalCost(243.0)
                                .build())
                        .build())
                .productType("Tinto")
                .products(new ProductDTO[]{
                        ProductDTO
                                .builder()
                                .code("1")
                                .type("Tinto")
                                .price(121.5)
                                .harvest("2019")
                                .year(2020)
                                .build(),
                        ProductDTO
                                .builder()
                                .code("2")
                                .type("Tinto")
                                .price(140.0)
                                .harvest("2019")
                                .year(2021)
                                .build(),
                        ProductDTO
                                .builder()
                                .code("3")
                                .type("Tinto")
                                .price(430.2)
                                .harvest("2007")
                                .year(2019)
                                .build()
                })
                .build();

        // Mocking ModelMapper behavior
        when(modelMapper.map(recommendation, RecommendationDTO.class)).thenReturn(expectedDTO);

        // When
        RecommendationDTO result = recommendationMapper.toDTO(recommendation);

        // Then
        assertEquals(expectedDTO, result);
    }

    @Test
    public void testToDTO_NullInput() {
        // Given
        Recommendation recommendation = null;

        // Mocking ModelMapper behavior
        when(modelMapper.map(null, RecommendationDTO.class)).thenReturn(null);

        // When
        RecommendationDTO actualDTO = recommendationMapper.toDTO(recommendation);

        // Then
        assertNull(actualDTO);
    }
}