package com.example.mstrabajadores.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
