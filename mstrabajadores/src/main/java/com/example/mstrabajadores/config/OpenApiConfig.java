package com.example.mstrabajadores.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para el microservicio de Trabajadores.
 * Define el título, descripción y versión que aparecen en Swagger UI
 * (accesible normalmente en /swagger-ui.html o /v3/api-docs).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Mstrabajadores API")
                                .description("Microservicio de gestión de trabajadores del proyecto Bosques Australes")
                                .version("1.0.0")
                );
    }
}