package com.example.mstrabajadores.model;

import jakarta.persistence.*;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "trabajadores")
@Schema(
        name = "Trabajador",
        description = "Representa a un trabajador del proyecto Bosques Australes"
)
public class TrabajadoresModel {
    //Aquí va la parte de documentar con Schema
    //desde el id hasta la fecha contrato, todos cuentan con la documentacion
    @Schema(description = "Identificador único del trabajador", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre completo del trabajador", example = "Pedro González")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Schema(description = "RUT del trabajador", example = "12345678-9")
    @Column(name = "rut", nullable = false, length = 12)
    private String rut;

    @Schema(description = "Estado actual del trabajador", example = "ACTIVO")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Schema(description = "Edad del trabajador", example = "35")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Schema(description = "Número de teléfono del trabajador", example = "+56912345678")
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Schema(description = "Correo electrónico del trabajador", example = "pedro.gonzalez@bosquesaustrales.cl")
    @Column(name = "correo", nullable = false)
    private String correo;

    @Schema(description = "Cargo que ocupa el trabajador dentro de la empresa", example = "OPERARIO")
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @Schema(description = "Fecha de inicio del contrato del trabajador", example = "2026-01-15")
    @Column(name = "fechaContrato", nullable = false)
    private Date fechaContrato;

    //Constructor vacio
    public TrabajadoresModel() {
    }

    //Constructor con caracteres
    public TrabajadoresModel(String nombre, String rut, Estado estado, Integer edad,
                             String telefono, String correo, Cargo cargo, Date fechaContrato) {
        this.nombre = nombre;
        this.rut = rut;
        this.estado = estado;
        this.edad = edad;
        this.telefono = telefono;
        this.correo = correo;
        this.cargo = cargo;
        this.fechaContrato = fechaContrato;
    }

    //Getters and setters
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
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