package com.example.despachoo.model;

import jakarta.persistence.*;

@Entity
@Table(name ="Despacho" )

public class despachoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombreDespachador", nullable = false, length = 100)
    private String nombreDespachador;
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private String estado;
    @Column(name = "lugarRecepcion", nullable = false)
    @Enumerated(EnumType.STRING)
    private String lugarRecepcion;
    @Column(name = "tipoPedido", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private String tipoPedido;
    @Column(name = "localidad", nullable = false)
    private String localidad;
    @Column(name = "factura", nullable = false, length = 25)
    private String factura;
    @Column(name = "trazabilidadCompleta", nullable = false)
    private String trazabilidadCompleta;
    @Column(name = "especie", nullable = false)
    @Enumerated(EnumType.STRING)
    private String especie;

    public despachoModel() {
    }

    public despachoModel(String nombreDespachador, String estado, String lugarRecepcion,
                         String tipoPedido, String localidad, String factura, String trazabilidadCompleta, String especie) {
        this.nombreDespachador = nombreDespachador;
        this.estado = estado;
        this.lugarRecepcion = lugarRecepcion;
        this.tipoPedido = tipoPedido;
        this.localidad = localidad;
        this.factura = factura;
        this.trazabilidadCompleta = trazabilidadCompleta;
        this.especie = especie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreDespachador() {
        return nombreDespachador;
    }

    public void setNombreDespachador(String nombreDespachador) {
        this.nombreDespachador = nombreDespachador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLugarRecepcion() {
        return lugarRecepcion;
    }

    public void setLugarRecepcion(String lugarRecepcion) {
        this.lugarRecepcion = lugarRecepcion;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
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
}
