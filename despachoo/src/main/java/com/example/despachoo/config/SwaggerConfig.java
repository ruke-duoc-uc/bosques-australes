package com.example.despachoo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para el microservicio de Despacho.
 * Define la metadata general que aparece en la documentación autogenerada
 * (accesible normalmente en /swagger-ui.html o /v3/api-docs).
 */
@Configuration
public class SwaggerConfig {

    public SwaggerConfig() {
    }

    //Bean que arma el objeto OpenAPI con título, descripción y versión de la API,
    //información que se muestra en la interfaz de Swagger UI.
    @Bean
    public OpenAPI configurarOpenApi() {
        return (new OpenAPI())
                .info((new Info())
                        .title("Despacho API")
                        .description("Microservicio de gestión de despacho")
                        .version("1.0.0"));
    }
}