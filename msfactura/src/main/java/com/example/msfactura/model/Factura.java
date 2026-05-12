package com.example.msfactura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* No podemos forzarlo a ser unico
    por la naturaleza de las emisiones en el SII
    una factura podria compartir numero con otra */
    // Opcion 1, agregar detalles de la factura por separado
    // Opcion 2, dejarlo todo en el mismo atributo



    //Datos factura
    @Column(name = "Factura")
    private Long factura;
    @Column(name = "giro",nullable = false)
    // El giro es la actividad economica que paga la factura
    private String giro;
    @Column(name = "monto",nullable = false)
    private Double monto;

    //Datos de Predio
    //idPredio | nombrePredio | direccion
    @Column(name = "direccion",nullable = false)
    private String direccion;
    @Column(name = "idPredio", nullable = false)
    private Long idPredio;
    @Column(name = "nombrePredio", nullable = false)
    private String nombrePredio;


    //Datos de cliente

    // La razonSocial se puede referir a una persona natural o empresa
    @Column(name = "razonSocial")
    private String razonSocial;
    @Column(name = "ciudad",nullable = false)
    private String ciudad;
    @Column(name = "comuna", nullable = false)
    private String comuna;
    @Column(name = "telefonoCliente")
    private String telefonoCliente;




    public Factura() {
    }

    public Factura(Long factura, String giro, Double monto) {
        this.factura = factura;
        this.giro = giro;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }
}