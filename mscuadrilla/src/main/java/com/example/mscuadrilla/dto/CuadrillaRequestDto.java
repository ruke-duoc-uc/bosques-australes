package com.example.mscuadrilla.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CuadrillaRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La zona es obligatoria")
    private String zona;

    @NotBlank(message = "Debe definir una especialidad")
    private String especialidad;

    @NotNull(message = "El estado debe ser true o false")
    private Boolean estado;

    private List<Long> trabajadoresIds;

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

    public List<Long> getTrabajadoresIds() {
        return trabajadoresIds;
    }

    public void setTrabajadoresIds(List<Long> trabajadoresIds) {
        this.trabajadoresIds = trabajadoresIds;
    }
}
