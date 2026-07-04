package com.example.mscuadrilla.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * OBJETO DE TRANSFERENCIA DE DATOS DE ENTRADA (REQUEST DTO)
 * Clase utilizada para interceptar y validar los datos enviados por un cliente externo (JSON)
 * al intentar registrar o modificar una cuadrilla en el sistema.
 * Asegura el desacoplamiento entre la capa de presentación externa y el modelo físico de persistencia,
 * actuando como un filtro de integridad en la frontera del microservicio.
 */
@Schema(name = "CuadrillaRequest", description = "Modelo de datos requerido para crear o actualizar una cuadrilla forestal")
public class CuadrillaRequestDto {
    /**
     * Nombre distintivo que se le asigna al equipo de operarios forestales.
     * Validación: No se permiten campos nulos, cadenas vacías ni espacios en blanco.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre identificatorio para el equipo de trabajo", example = "Cuadrilla Los Alerces", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    /**
     * Área geográfica o zona forestal específica donde el equipo ejecutará sus labores.
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "La zona es obligatoria")
    @Schema(description = "Ubicación geográfica asignada", example = "Región de Los Ríos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String zona;

    /**
     * Competencia o tarea técnica especializada que desempeña la cuadrilla (ej. Tala, Plantación).
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "Debe definir una especialidad")
    @Schema(description = "Tipo de labor principal a ejecutar en terreno", example = "Poda y Raleo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String especialidad;

    /**
     * Indicador lógico de disponibilidad operativa inmediata.
     * Validación: Al ser un tipo primitivo envuelto (Boolean), se obliga la presencia de un valor lógico estricto (true/false).
     */
    @NotNull(message = "El estado debe ser true o false")
    @Schema(description = "Define si la cuadrilla estará disponible para asignaciones de inmediato", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean estado;

    /**
     * Colección opcional que almacena las llaves primarias de los operarios pertenecientes al equipo.
     * Representa el enlace lógico distribuido hacia el microservicio externo de Trabajadores.
     */
    @Schema(description = "Listado opcional de identificadores de trabajadores a vincular desde el inicio", example = "[101, 102, 105]")
    private List<Long> trabajadoresIds;

    //getter y setters
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
