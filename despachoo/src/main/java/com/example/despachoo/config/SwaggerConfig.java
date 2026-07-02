package com.example.despachoo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public SwaggerConfig() {
    }

    @Bean
    public OpenAPI configurarOpenApi() {
        return (new OpenAPI()).info((new Info()).title("Despacho API").description("Microservicio de gestión de despacho").version("1.0.0"));
    }
}
