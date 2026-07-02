package com.example.msacopio.client;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component

public class EspeciesClient {
    private final RestClient restClient;
    public EspeciesClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8089/api/v1/especies").build();
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
