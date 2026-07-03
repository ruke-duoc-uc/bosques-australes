package com.example.msfactura.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class ClientesClient {
    private final RestClient restClient;

    public ClientesClient(RestClient.Builder builder, @Value("${MS_CLIENTE_URI:http://localhost:8081}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/cliente").build();
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