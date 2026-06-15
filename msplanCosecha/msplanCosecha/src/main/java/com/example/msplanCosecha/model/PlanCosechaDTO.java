package com.example.msplanCosecha.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Plan de cosecha | DTO",description = "Contiene toda la informacion que se requiere en el metodo que actualiza de forma parcial el plan " +
        "de cosecha")
public record PlanCosechaDTO(
        Long idEspecie,
        Double alturaPromedio,
        Long edadRodal,
        String descripcion
) {}