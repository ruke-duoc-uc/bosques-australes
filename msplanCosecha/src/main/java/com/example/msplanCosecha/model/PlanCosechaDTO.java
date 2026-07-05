package com.example.msplanCosecha.model;

import io.swagger.v3.oas.annotations.media.Schema;
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/

/*
    FacturaDTO se utiliza en el metodo PATCH para evitar que se
    el cuerpo de la actualizacion sea forzado a estar completo
 */
// @Schema describe la clase en Swagger, en este caso resume su proposito
@Schema(name = "Plan de cosecha | DTO",description = "Contiene toda la informacion que se requiere en el metodo que actualiza de forma parcial el plan " +
        "de cosecha")
public record PlanCosechaDTO(
        Long idEspecie,
        Double alturaPromedio,
        Long edadRodal,
        String descripcion
) {}