package com.example.seguridad.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * OBJETO DE TRANSFERENCIA DE DATOS DE ENTRADA (REQUEST DTO) - ASIGNACIÓN DE EPP
 * Encargado de validar los datos del cuerpo de las peticiones HTTP destinadas a registrar
 * la entrega de Equipos de Protección Personal a los operarios de Bosques Australes.
 */
public class EppRequestDto {
    /**
     * ID de control interno del trabajador que recibe la indumentaria de seguridad.
     */
    @NotNull(message = "El ID del trabajador es obligatorio")
    private Long trabajadorId;

    /**
     * Tipo o categoría del implemento entregado (ej: Casco, Botas con puntera).
     * Validación: Máximo 100 caracteres.
     */
    @NotBlank(message = "El tipo de EPP es obligatorio")
    @Size(max = 100)
    private String tipo;

    /**
     * Fecha en la que se hace la entrega material del equipo al operario forestal.
     */
    @NotNull(message = "La fecha de entrega es obligatoria")
    private String fechaEntrega;

    /**
     * Fecha límite recomendada de uso seguro antes de que el material pierda propiedades de protección.
     */
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @NotBlank(message = "La fecha de vencimiento es obligatoria")
    private String fechaVencimiento;

    /**
     * Glosa opcional para añadir detalles del lote, la marca o el estado del equipamiento.
     */
    @Size(max = 200)
    private String observaciones;


    //getter y setters
    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
