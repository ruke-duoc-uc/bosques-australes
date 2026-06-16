package com.example.mscuadrilla.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "CuadrillaRequest", description = "Modelo de datos requerido para crear o actualizar una cuadrilla forestal")
public class CuadrillaRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre identificatorio para el equipo de trabajo", example = "Cuadrilla Los Alerces", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "La zona es obligatoria")
    @Schema(description = "Ubicación geográfica asignada", example = "Región de Los Ríos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String zona;

    @NotBlank(message = "Debe definir una especialidad")
    @Schema(description = "Tipo de labor principal a ejecutar en terreno", example = "Poda y Raleo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String especialidad;

    @NotNull(message = "El estado debe ser true o false")
    @Schema(description = "Define si la cuadrilla estará disponible para asignaciones de inmediato", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean estado;

    @Schema(description = "Listado opcional de identificadores de trabajadores a vincular desde el inicio", example = "[101, 102, 105]")
    private List<Long> trabajadoresIds;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Long> getTrabajadoresIds() {
        return trabajadoresIds;
    }

    public void setTrabajadoresIds(List<Long> trabajadoresIds) {
        this.trabajadoresIds = trabajadoresIds;
    }
}
