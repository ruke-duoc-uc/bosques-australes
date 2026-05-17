package com.example.mspredios.model;
import jakarta.persistence.*;

@Entity
@Table(name = "PREDIOS")
public class Predios{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ciudad;

    @Column(name = "comuna",nullable = false)
    private String comuna;

    @Column(nullable = false)
    private String dueno;

    public Predios(String nombre, String ciudad, String comuna, String dueno) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.comuna = comuna;
        this.dueno = dueno;
    }

    public Predios() {
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