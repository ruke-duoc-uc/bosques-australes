package com.example.mspredios.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Predios")
public class Predios{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "nombre",nullable = false, length = 100)
    private String nombre;
    @NotNull
    @Column(name = "ciudad", nullable = false, length = 100)
    private String ciudad;
    @NotNull
    @Column(name = "comuna",nullable = false, length = 100)
    private String comuna;
    @NotNull
    @Column(name="dueño",nullable = false, length = 100)
    private String dueno;

    public Predios() {
    }

    public Predios(String nombre, String ciudad, String comuna, String dueno) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.comuna = comuna;
        this.dueno = dueno;
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

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }
}