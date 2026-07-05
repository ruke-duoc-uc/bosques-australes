package com.example.msacopio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Expone un RestClient.Builder como bean de Spring, para que EspeciesClient
 * (y cualquier otro cliente futuro) lo inyecte y arme su propio RestClient
 * agregando su baseUrl correspondiente.
 */
@Configuration
public class RestClientConfig {
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}