package com.br.webshop.purchase.mapper;

import com.br.webshop.purchase.dto.schemas.PurchaseDTO;
import com.br.webshop.purchase.domain.Purchase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Mapper class for converting between Purchase and PurchaseDTO.
 */
@Service
public class PurchaseMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructs the PurchaseMapper with a ModelMapper instance.
     *
     * @param modelMapper The ModelMapper instance to use for mapping objects.
     */
    @Autowired
    public PurchaseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a Purchase entity to a PurchaseDTO.
     *
     * @param purchase The Purchase entity to convert.
     * @return The mapped PurchaseDTO.
     */
    public PurchaseDTO toDTO(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDTO.class);
    }
}
