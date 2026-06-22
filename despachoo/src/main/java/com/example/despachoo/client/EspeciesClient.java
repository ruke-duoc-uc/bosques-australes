package com.example.despachoo.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import jakarta.persistence.EntityNotFoundException;

@Component
public class EspeciesClient {
    private final RestClient restClient;

    public EspeciesClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8083/api/especies").build();
    }

    public EspeciesDTO obtenerDatosEspecies(Long idEspecies){
        return restClient.get()
                .uri("/{id}", idEspecies)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new EntityNotFoundException("La especie con ID " + idEspecies + " no existe en el sistema.");
                }).body(EspeciesDTO.class);
    }
}
