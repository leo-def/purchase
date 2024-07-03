package com.br.webshop.purchase.controller;

import com.br.webshop.purchase.dto.ApiErrorResponseDTO;
import com.br.webshop.purchase.dto.schemas.RecommendationDTO;
import com.br.webshop.purchase.mapper.RecommendationMapper;
import com.br.webshop.purchase.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * REST controller for managing wine recommendations.
 */
@RestController
@RequestMapping("recomendacao")
@Tag(name = "Recommendation", description = "Endpoints for wine recommendations based on purchase history")
public class RecommendationController {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationController.class);

    private final RecommendationService recommendationService;
    private final RecommendationMapper recommendationMapper;

    /**
     * Constructs the RecommendationController with necessary services and mappers.
     *
     * @param recommendationService The service handling recommendation logic.
     * @param recommendationMapper  The mapper for converting entities to DTOs.
     */
    @Autowired
    public RecommendationController(
            RecommendationService recommendationService,
            RecommendationMapper recommendationMapper
    ) {
        this.recommendationService = recommendationService;
        this.recommendationMapper = recommendationMapper;
    }

    /**
     * Retrieves wine recommendations based on customer's purchase history.
     *
     * @return a {@link Flux} of {@link RecommendationDTO} representing wine recommendations.
     */
    @Operation(
            summary = "Get wine recommendations",
            description = "Returns wine recommendations based on the types of wine the customer buys the most."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecommendationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @GetMapping("/cliente/tipo")
    public Flux<RecommendationDTO> getRecommendation() {
        logger.info("Received request to get wine recommendations");
        return recommendationService.getRecommendations()
                .map(recommendationMapper::toDTO)
                .doOnNext(response -> logger.info("Emitting wine recommendation: {}", response))
                .doOnComplete(() -> logger.info("Completed emitting wine recommendations"));
    }
}
