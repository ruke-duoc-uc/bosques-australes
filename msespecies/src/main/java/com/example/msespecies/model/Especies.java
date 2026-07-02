package com.example.msespecies.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Especies")
public class Especies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
// @NotBlank se asegura que los atributos no esten vacios
// @Schema da una breve descripcion de el contenido del atributo, junto a una explicacion de 
// porque existe y su forma 
    @NotBlank
    @Column(name = "nombre", nullable = false)
    @Schema(name = "Nombre",
    description = "Nombre comun de la especie que se trabaja")
    private String nombre;

    @NotBlank
    @Column(name = "uso", nullable= false)
    @Schema(name = "Uso",
    description = "Aplicaciones comunes de la madera, ya sea en bruto o procesada")
    private String uso;

    @NotBlank
    @Column(name = "calidad", nullable = false)
    @Schema(name = "Calidad",
    description = "Descripcion breve de la resistencia a golpes, cortes, humedad, etcetera")
    private String calidad;

    @NotBlank
    @Column(name = "color")
    @Schema(name = "Color",
    description = "Color caracteristico de la madera")
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
