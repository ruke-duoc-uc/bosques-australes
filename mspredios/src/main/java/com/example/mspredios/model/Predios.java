package com.example.mspredios.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Predios")
public class Predios{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "nombre",nullable = false, length = 100)
    @Schema(name = "Nombre",
    description = "Como se llama el local, zona o extension de tierra")
    private String nombre;
    @NotBlank
    @Column(name = "ciudad", nullable = false, length = 100)
    @Schema(name = "Ciudad",
    description = "Ciudad en la que se ubica")
    private String ciudad;
    @NotBlank
    @Column(name = "comuna",nullable = false, length = 100)
    @Schema(name = "Comuna",
    description = "Comuna en la que se encuentra, ya que podrian haber mas de una en la misma ciudad" +
    "hacemos esta distincion por encima")
    private String comuna;
    @NotBlank
    @Column(name="dueño",nullable = false, length = 100)
    @Schema(name = "Direccion",
    description = "Direccion exacta del predio, es necesario mencionar la ciudad y comuna" +
            "para evitar confuciones por nombres o números de calle repetidos")
    private String direccion;

    public Predios() {
    }

    public Predios(String nombre, String ciudad, String comuna, String direccion) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.comuna = comuna;
        this.direccion = direccion;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }
}