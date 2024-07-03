package com.br.webshop.purchase.mapper;

import com.br.webshop.purchase.dto.schemas.CustomerDTO;
import com.br.webshop.purchase.domain.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Mapper class for converting between Customer and CustomerDTO.
 */
@Service
public class CustomerMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructs the CustomerMapper with a ModelMapper instance.
     *
     * @param modelMapper The ModelMapper instance to use for mapping objects.
     */
    @Autowired
    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a CustomerServiceDTO to a Customer entity.
     *
     * @param customerDTO The CustomerServiceDTO to convert.
     * @return The mapped Customer entity.
     */
    public Customer fromClientDTO(com.br.webshop.purchase.client.customerservice.dto.CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    /**
     * Converts a Customer entity to a CustomerDTO.
     *
     * @param customer The Customer entity to convert.
     * @return The mapped CustomerDTO.
     */
    public CustomerDTO toDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
