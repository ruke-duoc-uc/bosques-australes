package com.example.seguridad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class HabilitacionFaenaDto {
    @NotNull(message = "El ID del supervisor habilitador es obligatorio")
    private Long supervisorHabilitadorId;

    @NotBlank(message = "Las observaciones son obligatorias")
    @Size(min = 10, max = 300, message = "Mínimo 10, máximo 300 caracteres")
    private String observacionesHabilitacion;

    public Long getSupervisorHabilitadorId() {
        return supervisorHabilitadorId;
    }

    public void setSupervisorHabilitadorId(Long supervisorHabilitadorId) {
        this.supervisorHabilitadorId = supervisorHabilitadorId;
    }

    public String getObservacionesHabilitacion() {
        return observacionesHabilitacion;
    }

    public void setObservacionesHabilitacion(String observacionesHabilitacion) {
        this.observacionesHabilitacion = observacionesHabilitacion;
    }
}
