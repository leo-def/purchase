package com.br.webshop.purchase.controller;

import com.br.webshop.purchase.dto.schemas.ProductDTO;
import com.br.webshop.purchase.mapper.PurchaseMapper;
import com.br.webshop.purchase.service.DataCoordinatorService;
import com.br.webshop.purchase.service.PurchaseService;
import com.br.webshop.purchase.domain.Purchase;
import com.br.webshop.purchase.dto.schemas.PurchaseDTO;
import com.br.webshop.purchase.utils.DomainUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTests {

    @Mock
    private DataCoordinatorService dataCoordinatorService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchaseMapper purchaseMapper;

    @InjectMocks
    private PurchaseController purchaseController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(purchaseController).build();
    }

    @Test
    public void testGetPurchasesSortedByTotalCost() {
        Purchase purchase1 = DomainUtils.getPurchase1();
        PurchaseDTO purchaseDTO1 = PurchaseDTO
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
        Mockito.when(dataCoordinatorService.getEnrichedCustomerPurchases()).thenReturn(Flux.just(purchase1));
        Mockito.when(purchaseService.sortByTotalCost(any(Flux.class))).thenReturn(Flux.just(purchase1));
        Mockito.when(purchaseMapper.toDTO(purchase1)).thenReturn(purchaseDTO1);

        webTestClient.get().uri("/purchases/compras")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PurchaseDTO.class)
                .value(response -> {
                    assertThat(purchaseDTO1.getQuantity()).isEqualTo(1);
                    assertThat(purchaseDTO1.getTotalCost()).isEqualTo(430.3);
                    assertThat(purchaseDTO1.getProduct().getCode()).isEqualTo("4");
                    assertThat(purchaseDTO1.getProduct().getType()).isEqualTo("RosÊ");
                    assertThat(purchaseDTO1.getProduct().getPrice()).isEqualTo(430.3);
                    assertThat(purchaseDTO1.getProduct().getHarvest()).isEqualTo("2009");
                    assertThat(purchaseDTO1.getProduct().getYear()).isEqualTo(2020);
                });
    }

