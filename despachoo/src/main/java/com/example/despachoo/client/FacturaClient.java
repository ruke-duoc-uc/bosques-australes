package com.example.despachoo.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class FacturaClient {
    private final RestClient restClient;

    public FacturaClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8084/api/factura").build();;
    }

    public FacturaDTO obtenerDatosFactura(Long idFactura){
        return restClient.get()
                .uri("/{id}", idFactura)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Factura no encontrada");
                }).body(FacturaDTO.class);
    }

}
