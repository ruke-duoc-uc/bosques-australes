package com.example.mscuadrilla.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
/**
 * OBJETO DE TRANSFERENCIA DE DATOS DE SALIDA (RESPONSE DTO)
 * Clase modelada para estructurar el cuerpo de las respuestas HTTP (JSON) que devuelve el microservicio.
 * Su diseño es clave en arquitecturas distribuidas, ya que en lugar de exponer la entidad interna con solo
 * los IDs numéricos de los operarios, define un contenedor flexible ('List<Map<String, Object>>') para
 * inyectar y serializar los perfiles completos de los trabajadores recopilados en tiempo de ejecución.
 */

@Schema(name = "CuadrillaResponse", description = "Estructura de respuesta con los datos de salida de la cuadrilla")
public class CuadrillaResponseDto {
    /**
     * Identificador único de la cuadrilla registrado en la base de datos local.
     */
    @Schema(description = "ID único autogenerado por la base de datos", example = "1")
    private Long id;
    /**
     * Nombre operativo asignado al equipo forestal.
     */
    @Schema(description = "Nombre operativo de la cuadrilla", example = "Cuadrilla Los Alerces")
    private String nombre;
    /**
     * Zona o sector geográfico de despliegue en terreno.
     */
    @Schema(description = "Región forestal asignada", example = "Región de Los Ríos")
    private String zona;
    /**
     * Tarea técnica especializada del equipo (ej. Tala, Plantación, etc.).
     */
    @Schema(description = "Especialidad técnica del grupo", example = "Poda y Raleo")
    private String especialidad;
    /**
     * Estado operativo actual del equipo (true = Habilitado, false = Inactivo).
     */
    @Schema(description = "Estado de disponibilidad operativa", example = "true")
    private Boolean estado;
    /**
     * COMPOSICIÓN DE DATOS DISTRIBUIDOS:
     * Colección genérica y flexible estructurada para almacenar las respuestas JSON completas (pares clave/valor)
     * pertenecientes a cada operario, recuperadas dinámicamente desde el microservicio externo de Trabajadores.
     */

    @Schema(
            description = "Colección de objetos dinámicos que representan los datos completos de los operarios, inyectados desde el microservicio de Trabajadores",
            example = "[{\"id\": 101, \"nombre\": \"Juan Pérez\", \"cargo\": \"Motosierrista\"}]"
    )
    private List<Map<String, Object>> trabajadores;

    //getters y setters
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
