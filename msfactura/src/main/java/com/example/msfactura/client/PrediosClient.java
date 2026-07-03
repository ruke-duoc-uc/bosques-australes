package com.example.msfactura.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PrediosClient {
    private final RestClient restClient;

    public PrediosClient(RestClient.Builder builder, @Value("${MS_PREDIOS_URI:http://localhost:8080}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/predios").build();
    }

    public PrediosDTO obtenerDatosPredio(Long predioId) {
        return restClient.get()
                .uri("/{id}", predioId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Predio no encontrado");
                }).body(PrediosDTO.class);
    }
}