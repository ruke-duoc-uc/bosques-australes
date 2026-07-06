package com.example.msplanCosecha.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
    @Configuration es una marca que Spring lee al arrancar la aplicacion,
    hace que esta clase sea procesada en el arranque, permitiendo que los
    metodos @Bean se ejecuten en esta fase
 */
@Configuration
public class SwaggerConfig{
    /*
    @Bean es la marca que da la responsabilidad de manejar el metodo a Spring cuando
    la aplicacion arranque, en este caso solo construye el titulo y breve descripcion
    de Swagger
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("Plan Cosecha / Bosques Australes")
                .version("1.0")
                .description("Base de datos para planes de cosecha");
        ExternalDocumentation github = new ExternalDocumentation()
                .description("Repositorio del proyecto Bosques Australes")
                .url("https://github.com/ruke-duoc-uc/bosques-australes");
        return new OpenAPI()
                .info(info)
                .externalDocs(github);
}}