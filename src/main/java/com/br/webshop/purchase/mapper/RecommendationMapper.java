package com.br.webshop.purchase.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.br.webshop.purchase.dto.schemas.RecommendationDTO;
import com.br.webshop.purchase.domain.Recommendation;

/**
 * Mapper class for converting between Recommendation and RecommendationDTO.
 */
@Service
public class RecommendationMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructs the RecommendationMapper with a ModelMapper instance.
     *
     * @param modelMapper The ModelMapper instance to use for mapping objects.
     */
    @Autowired
    public RecommendationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a Recommendation entity to a RecommendationDTO.
     *
     * @param recommendation The Recommendation entity to convert.
     * @return The mapped RecommendationDTO.
     */
    public RecommendationDTO toDTO(Recommendation recommendation) {
        return modelMapper.map(recommendation, RecommendationDTO.class);
    }
}
