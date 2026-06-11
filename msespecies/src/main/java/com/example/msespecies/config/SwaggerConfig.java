package com.example.msespecies.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("Especies")
                .version("1.0")
                .description("Base de datos para especies de árboles");
        ExternalDocumentation github = new ExternalDocumentation()
                .description("Repositorio del proyecto Bosques Australes")
                .url("https://github.com/ruke-duoc-uc/bosques-australes");
        return new OpenAPI()
                .info(info)
                .externalDocs(github);
}}