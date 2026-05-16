package com.example.cuadrilla.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importante para Boolean
import java.util.List;

@Entity
@Table(name = "cuadrilla")
public class Cuadrilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "zona", nullable = false)
    @NotBlank(message = "La zona es obligatoria")
    private String zona;

    @Column(name = "especialidad", nullable = false)
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @Column(name = "estado", nullable = false)
    @NotNull(message = "El estado es obligatorio") // @NotBlank es solo para Strings, usa @NotNull para Boolean
    private Boolean estado;

    @ElementCollection
    @CollectionTable(name = "cuadrilla_trabajadores", joinColumns = @JoinColumn(name = "cuadrilla_id"))
    @Column(name = "trabajador_id")
    private List<Long> trabajadoresIds;

    // Constructor vacío (Obligatorio para JPA)
    public Cuadrilla() {
    }

    // Constructor completo (Ideal para tus pruebas y lógica)
    public Cuadrilla(String nombre, String zona, String especialidad, Boolean estado, List<Long> trabajadoresIds) {
        this.nombre = nombre;
        this.zona = zona;
        this.especialidad = especialidad;
        this.estado = estado;
        this.trabajadoresIds = trabajadoresIds;
    }

    // --- GETTERS Y SETTERS ---
    // (Asegúrate de que todos estén presentes, como los tenías)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public List<Long> getTrabajadoresIds() { return trabajadoresIds; }
    public void setTrabajadoresIds(List<Long> trabajadoresIds) { this.trabajadoresIds = trabajadoresIds; }
}