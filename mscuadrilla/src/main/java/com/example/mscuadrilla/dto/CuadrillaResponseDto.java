package com.example.mscuadrilla.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(name = "CuadrillaResponse", description = "Estructura de respuesta con los datos de salida de la cuadrilla")
public class CuadrillaResponseDto {
    @Schema(description = "ID único autogenerado por la base de datos", example = "1")
    private Long id;
    @Schema(description = "Nombre operativo de la cuadrilla", example = "Cuadrilla Los Alerces")
    private String nombre;
    @Schema(description = "Región forestal asignada", example = "Región de Los Ríos")
    private String zona;
    @Schema(description = "Especialidad técnica del grupo", example = "Poda y Raleo")
    private String especialidad;
    @Schema(description = "Estado de disponibilidad operativa", example = "true")
    private Boolean estado;

    @Schema(
            description = "Colección de objetos dinámicos que representan los datos completos de los operarios, inyectados desde el microservicio de Trabajadores",
            example = "[{\"id\": 101, \"nombre\": \"Juan Pérez\", \"cargo\": \"Motosierrista\"}]"
    )
    private List<Map<String, Object>> trabajadores;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Map<String, Object>> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Map<String, Object>> trabajadores) {
        this.trabajadores = trabajadores;
    }
}
