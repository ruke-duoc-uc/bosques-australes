package com.example.msfactura.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class ClientesClient {
    private final RestClient restClient;

    public ClientesClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8081/api/cliente").build();
    }

    public ClientesDTO obtenerDatosCliente(Long idCliente) {
        return restClient.get()
                .uri("/{id}", idCliente)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Cliente no encontrado");
                }).body(ClientesDTO.class);
    }
}