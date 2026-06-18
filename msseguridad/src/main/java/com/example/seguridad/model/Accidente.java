package com.example.seguridad.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accidentes")
@Schema(description = "Modelo que representa el registro de un accidente laboral")
public class Accidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Identificador del accidente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @Column(name = "trabajador_id", nullable = false)
    @Schema(example = "1", description = "Identificador de empleado")
    private Long trabajadorId;

    @Column(name = "cuadrilla_id", nullable = false)
    @Schema(example = "1", description = "Identificador de cuadrilla")
    private Long cuadrillaId;

    @Column(name = "fecha_hora_ocurrencia", nullable = false)
    @Schema(example = "2026-06-18T09:30:00", description = "Identificador de empleado")
    private String fechaHoraOcurrencia;

    @Schema(example = "2026-06-18T10:00:00")
    @Column(name = "fecha_hora_registro", nullable = false)
    private String fechaHoraRegistro;

    @Schema(example = "2026-06-18T10:00:00",description = "Caída de altura por mal uso de arnés de seguridad")
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "tipo_accidente", nullable = false)
    @Schema(description = "Categoría de accidente", example = "ACCIDENTE,CASI_ACCIDENTE", allowableValues = {"ACCIDENTE", "CASI_ACCIDENTE"})
    @Enumerated(EnumType.STRING)
    private TipoAccidente tipo;

    @Column(name = "gravedad", nullable = false)
    @Schema(description = "Categoría de gravedad", example = "FATAL,GRAVE,LEVE", allowableValues = {"LEVE", "GRAVE","FATAL"})
    @Enumerated(EnumType.STRING)
    private GravedadAccidente gravedad;

    @Column(name = "estado",nullable = false)
    @Schema(description = "Categoría de estado", example = "CERRADO,INVESTIGANDO,PENDIENTE", allowableValues = {"PENDIENTE", "INVESTIGANDO","CERRADO"})
    @Enumerated(EnumType.STRING)
    private EstadoAccidente estado;

    @Schema(description = "Fecha y hora del incidente")
    @Column(name = "observaciones_habilitacion", length = 300)
    private String observacionesHabilitacion;

    public Accidente() {
    }

    public Accidente(Long id, Long trabajadorId, Long cuadrillaId, String fechaHoraOcurrencia, String fechaHoraRegistro, String descripcion, TipoAccidente tipo, GravedadAccidente gravedad, EstadoAccidente estado, String observacionesHabilitacion) {
        this.id = id;
        this.trabajadorId = trabajadorId;
        this.cuadrillaId = cuadrillaId;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.gravedad = gravedad;
        this.estado = estado;
        this.observacionesHabilitacion = observacionesHabilitacion;
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

    public String getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(String fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
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

    public EstadoAccidente getEstado() {
        return estado;
    }

    public void setEstado(EstadoAccidente estado) {
        this.estado = estado;
    }

    public String getObservacionesHabilitacion() {
        return observacionesHabilitacion;
    }

    public void setObservacionesHabilitacion(String observacionesHabilitacion) {
        this.observacionesHabilitacion = observacionesHabilitacion;
    }
}
