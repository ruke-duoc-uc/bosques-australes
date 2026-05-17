package com.example.mstrabajadores.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "trabajadores")
public class TrabajadoresModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "rut", nullable = false, length = 12)
    private String rut;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Column(name = "edad", nullable = false)
    private Integer edad;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "correo", nullable = false)
    private String correo;
    @Column(name = "fechaContrato", nullable = false)
    private Date fechaContrato;

    public TrabajadoresModel() {
    }

    public TrabajadoresModel(String nombre, String rut, Estado estado, Integer edad,
                             String telefono, String correo, Date fechaContrato) {
        this.nombre = nombre;
        this.rut = rut;
        this.estado = estado;
        this.edad = edad;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaContrato = fechaContrato;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}