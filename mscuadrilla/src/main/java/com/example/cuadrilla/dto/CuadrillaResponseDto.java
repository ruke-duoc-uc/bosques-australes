package com.example.cuadrilla.dto;

import java.util.List;
import java.util.Map;

public class CuadrillaResponseDto {
    private Long id;
    private String nombre;
    private String zona;
    private String especialidad;
    private Boolean estado;

    // Aquí es donde guardaremos la lista que viene del otro MS
    private List<Map<String, Object>> trabajadores;

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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Map<String, Object>> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Map<String, Object>> trabajadores) {
        this.trabajadores = trabajadores;
    }
}
