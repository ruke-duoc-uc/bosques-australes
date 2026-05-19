package com.example.seguridad.dto;

import com.example.seguridad.model.GravedadAccidente;
import com.example.seguridad.model.TipoAccidente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AccidenteRequestDto {
    @NotNull(message = "El ID del trabajador es obligatorio")
    private Long trabajadorId;

    @NotNull(message = "El ID de la cuadrilla es obligatorio")
    private Long cuadrillaId;

    @NotNull(message = "La fecha y hora de ocurrencia es obligatoria")
    private String fechaHoraOcurrencia;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    @NotNull(message = "El tipo es obligatorio: ACCIDENTE o CASI_ACCIDENTE")
    private TipoAccidente tipo;

    @NotNull(message = "La gravedad es obligatoria: LEVE, GRAVE o FATAL")
    private GravedadAccidente gravedad;

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
