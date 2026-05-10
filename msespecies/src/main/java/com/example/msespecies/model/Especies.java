package com.example.msespecies.model;

import jakarta.persistence.*;

@Entity
public class Especies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "uso", nullable= false)
    private String uso;
    @Column(name = "calidad", nullable = false)
    private String calidad;
    @Column(name = "color")
    private String color;
    public Especies() {
    }

    public Especies(String nombre, String uso, String calidad, String color) {
        this.nombre = nombre;
        this.uso = uso;
        this.calidad = calidad;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
