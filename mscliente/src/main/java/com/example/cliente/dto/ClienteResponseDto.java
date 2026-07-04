package com.example.cliente.dto;

import com.example.cliente.model.TipoCliente;
import java.util.List;
import java.util.Map;
/**
 * OBJETO DE TRANSFERENCIA DE DATOS DE SALIDA (RESPONSE DTO)
 * Clase modelada para estructurar las respuestas HTTP (JSON) que el microservicio devuelve al cliente externo.
 * Actúa como una capa de proyección y abstracción de la entidad 'Cliente'. Su uso es una buena práctica crítica
 * ya que desacopla la API del diseño interno de las tablas de la base de datos, garantizando que si el Modelo JPA
 * cambia, la interfaz externa se mantenga estable, protegiendo además datos sensibles que no deban ser expuestos.
 */
public class ClienteResponseDto {
    /**
     * Identificador único del cliente asignado en la base de datos.
     */
    private long id;
    /**
     * Nombre comercial o de fantasía registrado del cliente.
     */
    private String nombre;
    /**
     * Rol Único Tributario (RUT) con formato de identificación fiscal.
     */
    private String rut;
    /**
     * Razón social legal e impositiva de la empresa u organización.
     */
    private String razonSocial;
    /**
     * Dirección de la casa matriz o instalaciones principales del cliente.
     */
    private String direccion;
    /**
     * Comuna de ubicación del domicilio comercial.
     */
    private String comuna;
    /**
     * Ciudad o centro urbano de operación principal.
     */
    private String ciudad;
    /**
     * Número telefónico de contacto corporativo.
     */
    private String telefono;
    /**
     * Correo electrónico para el envío de notificaciones y facturación.
     */
    private String email;
    /**
     * Clasificación o rol que cumple el cliente dentro del negocio (Enum TipoCliente).
     */
    private TipoCliente tipoCliente;
    /**
     * Estado lógico actual en la plataforma (true = Habilitado, false = Inactivo).
     */
    private Boolean estado;

    //getters and setters

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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
