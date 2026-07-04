package com.example.seguridad.dto;

import com.example.seguridad.model.GravedadAccidente;
import com.example.seguridad.model.TipoAccidente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * OBJETO DE TRANSFERENCIA DE DATOS DE ENTRADA (REQUEST DTO) - REPORTES DE ACCIDENTES
 * Clase encargada de capturar, aislar y validar estructuralmente el JSON enviado por los usuarios
 * al momento de notificar un siniestro o incidente en terreno.
 * Evita la exposición directa del modelo relacional en la frontera de la API del microservicio.
 */
public class AccidenteRequestDto {
    /**
     * Llave primaria lógica del operario afectado.
     * Validación: Campo estrictamente obligatorio.
     */
    @NotNull(message = "El ID del trabajador es obligatorio")
    private Long trabajadorId;

    /**
     * Llave primaria lógica de la cuadrilla donde ocurrió el evento.
     * Validación: Requerido para análisis de riesgos por zonas y equipos.
     */
    @NotNull(message = "El ID de la cuadrilla es obligatorio")
    private Long cuadrillaId;

    /**
     * Sello de tiempo que indica cuándo se materializó el evento en la faena.
     */
    @NotNull(message = "La fecha y hora de ocurrencia es obligatoria")
    private String fechaHoraOcurrencia;

    /**
     * Relato explícito de la dinámica del accidente.
     * Validación: Obligatorio, con un mínimo de 10 caracteres para garantizar una descripción útil.
     */
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    /**
     * Tipo de incidente enviado desde el cliente (Mapeado automáticamente al Enum TipoAccidente).
     */
    @NotNull(message = "El tipo es obligatorio: ACCIDENTE o CASI_ACCIDENTE")
    private TipoAccidente tipo;

    /**
     * Nivel de severidad técnica del suceso (Mapeado automáticamente al Enum GravedadAccidente).
     */
    @NotNull(message = "La gravedad es obligatoria: LEVE, GRAVE o FATAL")
    private GravedadAccidente gravedad;

    //getters y setters
    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public Long getCuadrillaId() {
        return cuadrillaId;
    }

    public void setCuadrillaId(Long cuadrillaId) {
        this.cuadrillaId = cuadrillaId;
    }

    public String getFechaHoraOcurrencia() {
        return fechaHoraOcurrencia;
    }

    public void setFechaHoraOcurrencia(String fechaHoraOcurrencia) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoAccidente getTipo() {
        return tipo;
    }

    public void setTipo(TipoAccidente tipo) {
        this.tipo = tipo;
    }

    public GravedadAccidente getGravedad() {
        return gravedad;
    }

    public void setGravedad(GravedadAccidente gravedad) {
        this.gravedad = gravedad;
    }
}
