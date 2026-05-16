package com.example.msplanCosecha.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EspeciesClient {
    private final RestClient restClient;

    public EspeciesClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082/api/especies").build();
    }

    public EspeciesDTO obtenerDatosCliente(Long idCliente) {
        return restClient.get()
                .uri("/{id}", idCliente)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Especie no encontrada");
                }).body(EspeciesDTO.class);
    }
}
