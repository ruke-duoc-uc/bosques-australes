package com.example.seguridad.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "epps")
@Schema(description = "Modelo que representa la entrega de Equipos de Protección Personal")
public class Epp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trabajadorId",nullable = false)
    @Schema(example = "1", description = "Identificador de el trabajador")
    private Long trabajadorId;

    @Column(name = "tipo",nullable = false, length = 100)
    @Schema(example = "CASCO", description = "Tipo de elemento entregado")
    private String tipo; // CASCO, CHALECO, GUANTES, BOTINES, PROTECTOR_AUDITIVO, etc.

    @Schema(example = "2026-06-18")
    @Column(name = "fechaEntrega",nullable = false)
    private String fechaEntrega;

    @Schema(example = "2027-06-18")
    @Column(name = "fechaVencimiento",nullable = false)
    private String fechaVencimiento;

    @Column(name = "activo",nullable = false)
    private boolean activo = true;

    @Schema(example = "2027-06-18",description = "una observacion de EPP entregado")
    @Column(name = "observaciones",length = 200)
    private String observaciones;

    public Epp() {
    }

    public Epp(Long trabajadorId, String tipo, String fechaEntrega, String fechaVencimiento, boolean activo, String observaciones) {
        this.trabajadorId = trabajadorId;
        this.tipo = tipo;
        this.fechaEntrega = fechaEntrega;
        this.fechaVencimiento = fechaVencimiento;
        this.activo = activo;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
