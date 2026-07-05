package com.example.despachoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Clase de configuración que expone un RestClient.Builder como bean de Spring.
 * Esto permite que EspeciesClient y FacturaClient (y cualquier otro cliente futuro)
 * puedan inyectar un RestClient.Builder ya listo, en vez de tener que construir
 * uno manualmente cada vez (evita repetir código de configuración).
 */
@Configuration //Le indica a Spring que esta clase define beans para el contexto de la aplicación.
public class RestClientConfig {

    public RestClientConfig() {
    }

    //Bean reutilizable: cada cliente que lo inyecte le agrega su propio baseUrl
    //(ver EspeciesClient/FacturaClient), pero parten de esta misma configuración base.
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}