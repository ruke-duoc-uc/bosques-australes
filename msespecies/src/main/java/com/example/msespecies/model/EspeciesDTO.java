package com.example.msespecies.model;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "Plan de cosecha | DTO",description = "Contiene toda la informacion que se requiere en el metodo que actualiza de forma parcial una especie")
public record EspeciesDTO(
        String nombre,
        String uso,
        String calidad,
        String color){}
