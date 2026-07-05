package com.example.seguridad.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * ENTIDAD DE DOMINIO (MODELO JPA) - CONTROL DE ASIGNACIÓN DE EPP
 * Mapea directamente la tabla de base de datos "epps" que gestiona la trazabilidad de los
 * Equipos de Protección Personal entregados a los operarios en las faenas de Bosques Australes.
 * Es crucial para mitigar riesgos legales y garantizar el cumplimiento de normativas de salud ocupacional.
 */
@Entity
@Table(name = "epps")
@Schema(description = "Modelo que representa la entrega de Equipos de Protección Personal")
public class Epp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Llave foránea lógica que apunta al operador forestal que recibió el equipamiento.
     * Enlace de carácter distribuido coordinado con el microservicio de Trabajadores.
     */
    @Column(name = "trabajadorId",nullable = false)
    @Schema(example = "1", description = "Identificador de el trabajador")
    private Long trabajadorId;

    /**
     * Tipo o categoría de elemento de protección provisto (ej. CASCO, BOTINES, PROTECTOR_AUDITIVO).
     */
    @Column(name = "tipo",nullable = false, length = 100)
    @Schema(example = "CASCO", description = "Tipo de elemento entregado")
    private String tipo; // CASCO, CHALECO, GUANTES, BOTINES, PROTECTOR_AUDITIVO, etc.

    /**
     * Fecha oficial en la cual el implemento de seguridad fue puesto a disposición del operario.
     */
    @Schema(example = "2026-06-18")
    @Column(name = "fechaEntrega",nullable = false)
    private String fechaEntrega;

    /**
     * Fecha límite recomendada por el fabricante para el reemplazo seguro del EPP debido a fatiga de material.
     */
    @Schema(example = "2027-06-18")
    @Column(name = "fechaVencimiento",nullable = false)
    private String fechaVencimiento;

    /**
     * Bandera lógica de validez. Un EPP activo (true) significa que el operario está cubierto actualmente,
     * mientras que false denota desuso, extravío o renovación obligatoria.
     */
    @Column(name = "activo",nullable = false)
    private boolean activo = true;

    /**
     * Glosa explicativa, detalles sobre tallas, marcas o justificaciones de recambios extraordinarios.
     */
    @Schema(example = "2027-06-18",description = "una observacion de EPP entregado")
    @Column(name = "observaciones",length = 200)
    private String observaciones;

    //constructor vacio
    public Epp() {
    }

    //constructor con argumentos
    public Epp(Long trabajadorId, String tipo, String fechaEntrega, String fechaVencimiento, boolean activo, String observaciones) {
        this.trabajadorId = trabajadorId;
        this.tipo = tipo;
        this.fechaEntrega = fechaEntrega;
        this.fechaVencimiento = fechaVencimiento;
        this.activo = activo;
        this.observaciones = observaciones;
    }

    //getter y setters
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
