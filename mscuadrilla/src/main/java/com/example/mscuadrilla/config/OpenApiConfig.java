package com.example.mscuadrilla.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CLASE DE CONFIGURACIÓN GLOBAL - INTEGRACIÓN CON SWAGGER / OPENAPI 3
 * Esta clase técnica es detectada por Spring Boot durante el arranque inicial de la aplicación.
 * Su objetivo fundamental es registrar un Bean de tipo 'OpenAPI' en el contenedor de inversión
 * de control (IoC). Este componente inyecta los metadatos institucionales (Duoc UC), versión,
 * descripción del negocio y los enlaces del repositorio Git, habilitando la generación automática
 * de la documentación interactiva en el entorno web de Swagger UI para el módulo de Cuadrillas.
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI configurarOpenApi() {
        // Información de contacto
        Contact contacto = new Contact()
                .name("Bosques Autrales Repositorio")
                .url("https://github.com/ruke-duoc-uc/bosques-australes");

        // Información principal de la API
        Info informacionApi = new Info()
                .description("""
                        API para la empresa
                        Bosques Australes
                        """)
                .version("1.0")
                .termsOfService("https://www.duoc.cl")
                .contact(contacto);
        // Documentación externa (GitHub)
        ExternalDocumentation github = new ExternalDocumentation()
                .description("Repositorio oficial del proyecto Bosques Australes")
                .url("https://github.com/ruke-duoc-uc/bosques-australes");
        // Configuración OpenAPI
        return new OpenAPI()
                .info(informacionApi)
                .externalDocs(github);
    }
}
