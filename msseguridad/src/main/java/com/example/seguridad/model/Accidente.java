package com.example.seguridad.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * ENTIDAD DE DOMINIO (MODELO JPA) - REGISTRO DE ACCIDENTABILIDAD
 * Esta clase representa físicamente la tabla "accidentes" en la base de datos.
 * Su rol en la arquitectura es centralizar y auditar los incidentes y casi-accidentes
 * ocurridos en terreno, vinculando de forma distribuida las llaves de los trabajadores
 * y las cuadrillas involucradas para propósitos de fiscalización y prevención de riesgos.
 */
@Entity
@Table(name = "accidentes")
@Schema(description = "Modelo que representa el registro de un accidente laboral")
public class Accidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Identificador del accidente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    /**
     * Identificador del trabajador afectado.
     * Representa un enlace lógico y distribuido hacia el microservicio de Trabajadores.
     */
    @NotNull
    @Column(name = "trabajador_id", nullable = false)
    @Schema(example = "1", description = "Identificador de empleado")
    private Long trabajadorId;

    /**
     * Identificador de la cuadrilla en la cual operaba el trabajador al momento del suceso.
     * Enlace lógico distribuido hacia el microservicio de Cuadrillas.
     */
    @Column(name = "cuadrilla_id", nullable = false)
    @Schema(example = "1", description = "Identificador de cuadrilla")
    private Long cuadrillaId;

    /**
     * Fecha y hora exacta en la que aconteció el evento en terreno (formato ISO u horizontal).
     */
    @Column(name = "fecha_hora_ocurrencia", nullable = false)
    @Schema(example = "2026-06-18T09:30:00", description = "Identificador de empleado")
    private String fechaHoraOcurrencia;

    /**
     * Sello de tiempo automático o manual que indica cuándo se ingresó el reporte al sistema informático.
     */
    @Schema(example = "2026-06-18T10:00:00")
    @Column(name = "fecha_hora_registro", nullable = false)
    private String fechaHoraRegistro;

    /**
     * Relato detallado que especifica la dinámica del accidente o la falla de seguridad detectada.
     */
    @Schema(example = "2026-06-18T10:00:00",description = "Caída de altura por mal uso de arnés de seguridad")
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    /**
     * Clasificación categórica del suceso persistida como String (Enum TipoAccidente).
     * Distingue si llegó a materializarse un daño físico o si quedó catalogado como un desvío o peligro menor.
     */
    @Column(name = "tipo_accidente", nullable = false)
    @Schema(description = "Categoría de accidente", example = "ACCIDENTE,CASI_ACCIDENTE", allowableValues = {"ACCIDENTE", "CASI_ACCIDENTE"})
    @Enumerated(EnumType.STRING)
    private TipoAccidente tipo;

    /**
     * Nivel de impacto a la integridad del operador o los activos persistido como String (Enum GravedadAccidente).
     */
    @Column(name = "gravedad", nullable = false)
    @Schema(description = "Categoría de gravedad", example = "FATAL,GRAVE,LEVE", allowableValues = {"LEVE", "GRAVE","FATAL"})
    @Enumerated(EnumType.STRING)
    private GravedadAccidente gravedad;

    /**
     * Estado del flujo de auditoría y análisis de causa raíz persistido como String (Enum EstadoAccidente).
     */
    @Column(name = "estado",nullable = false)
    @Schema(description = "Categoría de estado", example = "CERRADO,INVESTIGANDO,PENDIENTE", allowableValues = {"PENDIENTE", "INVESTIGANDO","CERRADO"})
    @Enumerated(EnumType.STRING)
    private EstadoAccidente estado;

    /**
     * Comentarios adicionales, acciones correctivas tomadas o dictámenes de habilitación médica/operativa.
     */
    @Schema(description = "Fecha y hora del incidente")
    @Column(name = "observaciones_habilitacion", length = 300)
    private String observacionesHabilitacion;

    //constructor vacio
    public Accidente() {
    }

    //constructor con argumento
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

    //getters y setters
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
