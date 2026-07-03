package com.example.despachoo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class FacturaClient {
    private final RestClient restClient;

    public FacturaClient(RestClient.Builder builder, @Value("${MS_FACTURA_URI:http://localhost:8084}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/factura").build();
    }

    public FacturaDTO obtenerDatosFactura(Long idFactura) {
        return (FacturaDTO)this.restClient.get().uri("/{id}", new Object[]{idFactura}).retrieve().onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
            throw new RuntimeException("Factura no encontrada");
        }).body(FacturaDTO.class);
    }
}
