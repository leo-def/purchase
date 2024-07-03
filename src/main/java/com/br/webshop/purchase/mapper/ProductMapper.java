package com.br.webshop.purchase.mapper;

import com.br.webshop.purchase.dto.schemas.ProductDTO;
import com.br.webshop.purchase.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Mapper class for converting between Product and ProductDTO.
 */
@Service
public class ProductMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructs the ProductMapper with a ModelMapper instance.
     *
     * @param modelMapper The ModelMapper instance to use for mapping objects.
     */
    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a ProductServiceDTO to a Product entity.
     *
     * @param productDTO The ProductServiceDTO to convert.
     * @return The mapped Product entity.
     */
    public Product fromClientDTO(com.br.webshop.purchase.client.productservice.dto.ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    /**
     * Converts a Product entity to a ProductDTO.
     *
     * @param product The Product entity to convert.
     * @return The mapped ProductDTO.
     */
    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
