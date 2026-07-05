package com.example.msplanCosecha.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/*
    @Component es una marca que registra esta clase en el contenedor de Spring,
    permitiendo que se le inyecte el RestClient.Builder configurado previamente.
 */
@Component
public class EspeciesClient {
    private final RestClient restClient;

    // Aquí construimos la URL base para comunicarnos con msespecies
    public EspeciesClient(RestClient.Builder builder, @Value("${MS_ESPECIES_URI:http://localhost:8082}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl + "/api/especies").build();
    }

    /*
        Ya que solo necesitamos los datos de una especie a la vez, creamos un método que termine de
        construir la URL adecuada. Las capas superiores alimentan la consulta con un ID y se deriva
        esta petición al msespecies.
     */
    public EspeciesDTO obtenerDatosEspecie(Long idEspecie) { // Nota: Cambié el nombre del método y parámetro para que coincida con Especie
        return restClient.get()
                .uri("/{id}", idEspecie)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Especie no encontrada");
                }).body(EspeciesDTO.class);
    }
}