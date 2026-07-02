package com.example.despachoo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "Despacho"
)
public class DespachoModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "nombreDespachador",
            nullable = false,
            length = 100
    )
    private String nombreDespachador;
    @Column(
            name = "estado",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private estado estado;
    @Column(
            name = "lugarRecepcion",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private lugarRecepcion lugarRecepcion;
    @Column(
            name = "tipoPedido",
            nullable = false,
            length = 15
    )
    @Enumerated(EnumType.STRING)
    private tipoPedido tipoPedido;
    @Column(
            name = "Localidad",
            nullable = false
    )
    private String localidad;
    @Column(
            name = "factura",
            nullable = false,
            length = 25
    )
    private Long factura;
    @Column(
            name = "trazabilidadCompleta",
            nullable = false
    )
    private String trazabilidadCompleta;
    @Column(
            name = "especie",
            nullable = false
    )
    private String especie;

    public DespachoModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDespachador() {
        return this.nombreDespachador;
    }

    public void setNombreDespachador(String nombreDespachador) {
        this.nombreDespachador = nombreDespachador;
    }

    public estado getEstado() {
        return this.estado;
    }

    public void setEstado(estado estado) {
        this.estado = estado;
    }

    public lugarRecepcion getLugarRecepcion() {
        return this.lugarRecepcion;
    }

    public void setLugarRecepcion(lugarRecepcion lugarRecepcion) {
        this.lugarRecepcion = lugarRecepcion;
    }

    public tipoPedido getTipoPedido() {
        return this.tipoPedido;
    }

    public void setTipoPedido(tipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getFactura() {
        return this.factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public String getTrazabilidadCompleta() {
        return this.trazabilidadCompleta;
    }

    public void setTrazabilidadCompleta(String trazabilidadCompleta) {
        this.trazabilidadCompleta = trazabilidadCompleta;
    }

    public String getEspecie() {
        return this.especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public DespachoModel(String nombreDespachador, estado estado, lugarRecepcion lugarRecepcion, tipoPedido tipoPedido, String localidad, Long factura, String trazabilidadCompleta, String especie) {
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
