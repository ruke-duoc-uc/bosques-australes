package com.example.msfactura.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
/*
    @Component es una marca que avisa a Spring que le de un Builder
    el cual sera RestClientConfig
 */
@Component
public class ClientesClient {
    private final RestClient restClient;
// Aqui contruimos la URL base para comunicarnos con mscliente
    public ClientesClient(RestClient.Builder builder, @Value("${MS_CLIENTE_URI:http://localhost:8081}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/cliente").build();
    }
/*
    Ya que solo nesecitamos los datos de un cliente a la vez, solo creamos un metodo que termine de
    contruir la URL adecuada. El controller alimenta al service con un ID y se deriva esta consulta
    a el mscliente.
    Los metodos POST, PUT y PATCH son los unicos que se apoyan de este metodo
 */
    public ClientesDTO obtenerDatosCliente(Long idCliente) {
        return restClient.get()
                .uri("/{id}", idCliente)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Cliente no encontrado");
                }).body(ClientesDTO.class);
    }
}