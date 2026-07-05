package com.example.mspredios.model;
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/

import io.swagger.v3.oas.annotations.media.Schema;

/*
    PrediosDTO se utiliza en el metodo PATCH para evitar que se
    el cuerpo de la actualizacion sea forzado a estar completo
 */
// @Schema describe la funcion del DTO en el servicio
@Schema(name = "Plan de cosecha | DTO",description = "Contiene toda la informacion que se requiere en el metodo que actualiza de forma parcial una especie")
public record PrediosDTO(
    String nombre,
    String ciudad,
    String comuna,
    String direccion
) {
}
