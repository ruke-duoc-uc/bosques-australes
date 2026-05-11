package com.example.msfactura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La razonSocial se puede referir a una persona natural o empresa
    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name= "descripcion",nullable = false)
    private String descripcion;

    @Column(name = "idPredio", nullable = false)
    private Long idPredio;

    @Column(name = "nombrePredio", nullable = false)
    private String nombrePredio;

    // El giro es la actividad economica de la factura
    @Column(name = "giro",nullable = false)
    private String giro;
    // Estos son los datos de la venta
    @Column(name = "ciudad",nullable = false)
    private String ciudad;
    @Column(name = "comuna", nullable = false)
    private String comuna;

    @Column(name = "direccion",nullable = false)
    private String direccion;
    @Column(name = "monto",nullable = false)
    private Double monto;

    @Column(name = "telefonoCliente")
    private String telefonoCliente;

    public Factura() {
    }

    public Factura(Double monto, String giro, String descripcion, String razonSocial, String ciudad, String direccion, String comuna) {
        this.monto = monto;
        this.giro = giro;
        this.descripcion = descripcion;
        this.razonSocial = razonSocial;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.comuna = comuna;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Long idPredio) {
        this.idPredio = idPredio;
    }

    public String getNombrePredio() {
        return nombrePredio;
    }

    public void setNombrePredio(String nombrePredio) {
        this.nombrePredio = nombrePredio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
}