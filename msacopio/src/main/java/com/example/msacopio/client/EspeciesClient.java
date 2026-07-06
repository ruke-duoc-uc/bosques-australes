package com.example.msacopio.client;

import org.springframework.beans.factory.annotation.Value;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP encargado de comunicarse con el microservicio de Especies.
 */
@Component
public class EspeciesClient {
    private final RestClient restClient;

    //Se arma el RestClient con la URL base del microservicio de Especies,
    //configurable vía la variable de entorno/propiedad MS_ESPECIES_URI
    //(por defecto: http://localhost:8082).
    public EspeciesClient(RestClient.Builder builder, @Value("${MS_ESPECIES_URI:http://localhost:8082}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/especies").build();
    }

    //Realiza un GET a /api/especies/{id}.
    //Nota: el parámetro se llama "idCliente" pero en realidad representa el id de una ESPECIE,
    //no de un cliente — es solo un nombre un poco confuso, no afecta el funcionamiento.
    public EspeciesDTO obtenerDatosCliente(Long idCliente) {
        return restClient.get()
                .uri("/{id}", idCliente)
                .retrieve()
                //Si la especie no existe (4xx), se lanza EntityNotFoundException directamente,
                //que el GlobalExceptionHandler capturará y convertirá en un 404 con mensaje claro.
                //(Esto es más prolijo que en despachoo, donde se lanzaba una RuntimeException genérica).
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new EntityNotFoundException("La especie con ID " + idCliente + " no existe en el sistema.");
                }).body(EspeciesDTO.class);
    }
}