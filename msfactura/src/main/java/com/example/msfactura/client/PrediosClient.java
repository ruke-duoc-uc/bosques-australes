package com.example.msfactura.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/*
    @Component es una marca que avisa a Spring que le de un Builder
    el cual sera RestClientConfig
 */
@Component
public class PrediosClient {
    private final RestClient restClient;

    // Aqui construimos la URL base para comunicarnos con mspredios
    public PrediosClient(RestClient.Builder builder, @Value("${MS_PREDIOS_URI:http://localhost:8080}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/predios").build();
    }

    /*
        Ya que solo necesitamos los datos de un predio a la vez, solo creamos un metodo que termine de
        construir la URL adecuada. El controller alimenta al service con un ID y se deriva esta consulta
        a el mspredios.
        Los metodos POST, PUT y PATCH son los unicos que se apoyan de este metodo
     */
    public PrediosDTO obtenerDatosPredio(Long predioId) {
        return restClient.get()
                .uri("/{id}", predioId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Predio no encontrado");
                }).body(PrediosDTO.class);
    }
}