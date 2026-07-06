package com.example.despachoo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP encargado de comunicarse con el microservicio de Especies (mstrabajadores/msacopio).
 * Encapsula la llamada REST para no tener lógica de HTTP repartida por el Service.
 */
@Component //Se registra como bean para poder inyectarlo donde se necesite (ej: en DespachoService).
public class EspeciesClient {

    private final RestClient restClient;

    //@Value inyecta la URL base del microservicio de Especies desde application.properties/yml.
    //Si no está definida, usa "http://localhost:8082" como valor por defecto.
    public EspeciesClient(RestClient.Builder builder, @Value("${MS_ESPECIES_URI:http://localhost:8082}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/especies").build();
    }

    //Realiza un GET a /api/especies/{id} y mapea la respuesta a un EspeciesDTO.
    public EspeciesDTO obtenerDatosEspecies(Long idEspecies) {
        return (EspeciesDTO)this.restClient.get()
                .uri("/{id}", new Object[]{idEspecies})
                .retrieve()
                //Si la respuesta es un error 4xx (ej: 404 porque no existe la especie),
                //se lanza una excepción en vez de intentar mapear un body inexistente/erróneo.
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Especie no encontrada");
                })
                .body(EspeciesDTO.class);
    }
}