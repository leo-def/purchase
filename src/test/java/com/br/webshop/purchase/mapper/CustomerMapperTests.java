package com.br.webshop.purchase.mapper;

import com.br.webshop.purchase.dto.schemas.CustomerDTO;
import com.br.webshop.purchase.domain.Customer;

import com.br.webshop.purchase.dto.schemas.ProductDTO;
import com.br.webshop.purchase.dto.schemas.PurchaseDTO;
import com.br.webshop.purchase.dto.schemas.PurchaseSummaryDTO;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerMapperTests {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testFromClientDTO() {
        // Given
        com.br.webshop.purchase.client.customerservice.dto.CustomerDTO clientDTO =
                com.br.webshop.purchase.client.customerservice.dto.CustomerDTO
                        .builder()
                        .name("John Doe")
                        .citizenId("999888877733")
                        .purchases(
                                new com.br.webshop.purchase.client.customerservice.dto.PurchaseDTO[]{
                                        com.br.webshop.purchase.client.customerservice.dto.PurchaseDTO
                                                .builder()
                                                .code("1")
                                                .quantity(1)
                                                .build()
                                })
                        .build();

        Customer expectedCustomer = DomainUtils.getNotEnrichedCustomer1();

        when(modelMapper.map(clientDTO, Customer.class)).thenReturn(expectedCustomer);

        // When
        Customer result = customerMapper.fromClientDTO(clientDTO);

        // Then
        assertEquals(expectedCustomer, result);
    }

    @Test
    public void testToDTO() {
        // Given
        Customer customer = DomainUtils.getCustomer1();

        CustomerDTO expectedDTO = CustomerDTO.builder()
                .name("John Doe")
                .citizenId("999888877733")
                .purchaseSummary(PurchaseSummaryDTO
                        .builder()
                        .count(1)
                        .totalCost(430.3)
                        .build())
                .purchases(
                        new PurchaseDTO[]{
                                PurchaseDTO
                                        .builder()
                                        .quantity(1)
                                        .product(ProductDTO
                                                .builder()
                                                .code("4")
                                                .type("RosÊ")
                                                .price(430.3)
                                                .harvest("2009")
                                                .year(2020)
                                                .build())
                                        .build()
                        })
                .build();

        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(expectedDTO);

        // When
        CustomerDTO result = customerMapper.toDTO(customer);

        // Then
        assertEquals(expectedDTO, result);
    }
}
