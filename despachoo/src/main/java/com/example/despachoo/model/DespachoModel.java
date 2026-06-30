package com.example.despachoo.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name ="Despacho" )
@Schema(
        name = "Despacho",
        description = "Representa un despacho de productos forestales dentro del sistema"
)
public class DespachoModel {
    @Schema(description = "Identificador único del despacho", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Nombre de la persona encargada de realizar el despacho", example = "Juan Pérez")
    @Column(name = "nombreDespachador", nullable = false, length = 100)
    private String nombreDespachador;
    @Schema(description = "Estado actual del despacho", example = "GENERADA")
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private estado estado;
    @Schema(description = "Lugar donde se recibe el despacho", example = "BODEGA")
    @Column(name = "lugarRecepcion", nullable = false)
    @Enumerated(EnumType.STRING)
    private lugarRecepcion lugarRecepcion;
    @Schema(description = "Tipo de pedido asociado al despacho", example = "EXPORTACION")
    @Column(name = "tipoPedido", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private tipoPedido tipoPedido;
    @Schema(description = "Localidad de destino del despacho", example = "Valdivia")
    @Column(name = "Localidad", nullable = false)
    private String localidad;
    @Schema(description = "Número de factura asociada al despacho", example = "1023")
    @Column(name = "factura", nullable = false, length = 25)
    private Long factura;
    @Schema(description = "Código de trazabilidad completa del despacho", example = "TRZ-2026-00123")
    @Column(name = "trazabilidadCompleta", nullable = false)
    private String trazabilidadCompleta;
    @Schema(description = "Especie forestal asociada al despacho", example = "Pino Radiata")
    @Column(name = "especie", nullable = false)
    private String especie;

    public DespachoModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDespachador() {
        return nombreDespachador;
    }

    public void setNombreDespachador(String nombreDespachador) {
        this.nombreDespachador = nombreDespachador;
    }

    public estado getEstado() {
        return estado;
    }

    public void setEstado(estado estado) {
        this.estado = estado;
    }

    public lugarRecepcion getLugarRecepcion() {
        return lugarRecepcion;
    }

    public void setLugarRecepcion(lugarRecepcion lugarRecepcion) {
        this.lugarRecepcion = lugarRecepcion;
    }

    public tipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(tipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public String getTrazabilidadCompleta() {
        return trazabilidadCompleta;
    }

    public void setTrazabilidadCompleta(String trazabilidadCompleta) {
        this.trazabilidadCompleta = trazabilidadCompleta;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public DespachoModel(String nombreDespachador, estado estado, lugarRecepcion lugarRecepcion,
                         tipoPedido tipoPedido, String localidad, Long factura,
                         String trazabilidadCompleta, String especie) {
        this.nombreDespachador = nombreDespachador;
        this.estado = estado;
        this.lugarRecepcion = lugarRecepcion;
        this.tipoPedido = tipoPedido;
        this.localidad = localidad;
        this.factura = factura;
        this.trazabilidadCompleta = trazabilidadCompleta;
        this.especie = especie;


    }

}
