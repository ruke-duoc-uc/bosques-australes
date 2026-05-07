package com.example.msfactura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "descripcion",nullable = false)
    private String descripcion;

    @Column(name = "idPredio", nullable = false)
    private Long idPredio;

    @Column(name = "nombrePredio", nullable = false)
    private String nombrePredio;
    /*
    razonSocial
    monto
     */

    public Factura(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Factura() {
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
}