package com.example.cliente.model;

import jakarta.persistence.*;


@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "rut", nullable = false, length = 100)
    private String rut;
    @Column(name = "razonSocial", nullable = false)
    private String razonSocial;
    @Column(name = "giro", nullable = false)
    private Double giro;
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @Column(name = "comuna", nullable = false)
    private String comuna;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "tipoCliente", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    public Cliente() {
    }

    public Cliente(String nombre, String rut, String razonSocial, Double giro, String direccion, String comuna, String telefono, String email, TipoCliente tipoCliente, boolean estado) {
        this.nombre = nombre;
        this.rut = rut;
        this.razonSocial = razonSocial;
        this.giro = giro;
        this.direccion = direccion;
        this.comuna = comuna;
        this.telefono = telefono;
        this.email = email;
        this.tipoCliente = tipoCliente;
        this.estado = estado;
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

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Double getGiro() {
        return giro;
    }

    public void setGiro(Double giro) {
        this.giro = giro;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}