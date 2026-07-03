package com.example.msacopio.client;

import org.springframework.beans.factory.annotation.Value;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component

public class EspeciesClient {
    private final RestClient restClient;
    public EspeciesClient(RestClient.Builder builder, @Value("${MS_ESPECIES_URI:http://localhost:8082}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/especies").build();
    }
    public EspeciesDTO obtenerDatosCliente(Long idCliente) {
        return restClient.get()
                .uri("/{id}", idCliente)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new EntityNotFoundException("La especie con ID " + idCliente + " no existe en el sistema.");
                }).body(EspeciesDTO.class);
    }

}
