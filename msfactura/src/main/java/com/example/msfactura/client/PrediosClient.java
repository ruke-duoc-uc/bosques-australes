package com.example.msfactura.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PrediosClient {
    private final RestClient restClient;

    public PrediosClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080").build();
    }

    public PrediosDTO obtenerDatosPredio(Long predioId) {
        return restClient.get()
            .uri("/api/v1/predios/{id}", predioId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
            throw new RuntimeException("Predio no encontrado");
            }).body(PrediosDTO.class);
    }
}