package com.example.seguridad.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accidentes")
public class Accidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "trabajador_id", nullable = false)
    private Long trabajadorId;
    @Column(name = "cuadrilla_id", nullable = false)
    private Long cuadrillaId;
    @Column(name = "fecha_hora_ocurrencia", nullable = false)
    private String fechaHoraOcurrencia;
    @Column(name = "fecha_hora_registro", nullable = false)
    private String fechaHoraRegistro;
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAccidente tipo;
    @Column(name = "gravedad", nullable = false)
    @Enumerated(EnumType.STRING)
    private GravedadAccidente gravedad;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoAccidente estado;
    @Column(name = "supervisor_habilitador_id")
    private Long supervisorHabilitadorId;
    @Column(name = "fecha_habilitacion")
    private String fechaHabilitacion;
    @Column(name = "observaciones_habilitacion", length = 300)
    private String observacionesHabilitacion;

    public Accidente() {
    }

    public Accidente(Long trabajadorId, Long cuadrillaId, String fechaHoraOcurrencia, String fechaHoraRegistro, String descripcion, TipoAccidente tipo, GravedadAccidente gravedad, EstadoAccidente estado,Long supervisorHabilitadorId, String fechaHabilitacion, String observacionesHabilitacion) {
        this.trabajadorId = trabajadorId;
        this.cuadrillaId = cuadrillaId;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.gravedad = gravedad;
        this.estado = estado;
        this.supervisorHabilitadorId = supervisorHabilitadorId;
        this.fechaHabilitacion = fechaHabilitacion;
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

    public Long getSupervisorHabilitadorId() {
        return supervisorHabilitadorId;
    }

    public void setSupervisorHabilitadorId(Long supervisorHabilitadorId) {
        this.supervisorHabilitadorId = supervisorHabilitadorId;
    }

    public String getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(String fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public String getObservacionesHabilitacion() {
        return observacionesHabilitacion;
    }

    public void setObservacionesHabilitacion(String observacionesHabilitacion) {
        this.observacionesHabilitacion = observacionesHabilitacion;
    }
}
