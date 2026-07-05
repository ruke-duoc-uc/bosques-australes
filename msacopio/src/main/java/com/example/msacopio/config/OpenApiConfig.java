package com.example.msacopio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para el microservicio de Acopio.
 * Define el título, descripción y versión que aparecen en Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Msacopio API")
                                .description("Microservicio de gestión de acopio de productos del proyecto Bosques Australes")
                                .version("1.0.0")
                );
    }
}