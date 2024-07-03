package com.br.webshop.purchase.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating a ModelMapper bean.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Creates and configures a ModelMapper bean.
     *
     * @return The configured {@link ModelMapper} instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}