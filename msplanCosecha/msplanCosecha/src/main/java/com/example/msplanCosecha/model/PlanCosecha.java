package com.example.msplanCosecha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "planCosecha")
public class PlanCosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Datos PlanCosecha
    @Column(name = "edadRodal")
    private Long edadRodal;
    @Column(name = "alturaPromedio")
    private Double alturaPromedio;

    //Datos especie
    @Column(name = "especie")
    private String nombre;

    public PlanCosecha(Long edadRodal, Double alturaPromedio) {
        this.edadRodal = edadRodal;
        this.alturaPromedio = alturaPromedio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEdadRodal() {
        return edadRodal;
    }

    public void setEdadRodal(Long edadRodal) {
        this.edadRodal = edadRodal;
    }

    public Double getAlturaPromedio() {
        return alturaPromedio;
    }

    public void setAlturaPromedio(Double alturaPromedio) {
        this.alturaPromedio = alturaPromedio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
