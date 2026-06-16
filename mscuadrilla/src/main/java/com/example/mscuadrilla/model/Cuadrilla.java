package com.example.mscuadrilla.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importante para Boolean
import java.util.List;


@Entity
@Table(name = "cuadrilla")
@Schema(
        name = "Cuadrilla",
        description = "Entidad que representa una cuadrilla operativa de trabajo en terreno para BosquesAustrales"
)
public class Cuadrilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Identificador único de la cuadrilla", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre asignado a la cuadrilla operativa", example = "Cuadrilla Los Alerces")
    private String nombre;

    @Column(name = "zona", nullable = false)
    @NotBlank(message = "La zona es obligatoria")
    @Schema(description = "Zona forestal o sector geográfico de trabajo asignado", example = "Región de Los Ríos")
    private String zona;

    @Column(name = "especialidad", nullable = false)
    @NotBlank(message = "La especialidad es obligatoria")
    @Schema(description = "Labor o tarea principal que realiza el equipo", example = "Poda y Raleo", allowableValues = {"TALA", "PODA_Y_RALEO", "TRANSPORTE", "PLANTACIÓN"})
    private String especialidad;

    @Column(name = "estado", nullable = false)
    @NotNull(message = "El estado es obligatorio") // @NotBlank es solo para Strings, usa @NotNull para Boolean
    @Schema(description = "Indica si la cuadrilla se encuentra activa y operativa", example = "true", defaultValue = "true")
    private Boolean estado;

    @ElementCollection
    @CollectionTable(name = "cuadrilla_trabajadores", joinColumns = @JoinColumn(name = "cuadrilla_id"))
    @Column(name = "trabajador_id")
    @Schema(description = "Lista de IDs de los trabajadores asignados (vienen de forma distribuida desde el microservicio de Trabajadores)", example = "[101, 102, 105]")
    private List<Long> trabajadoresIds;

    // Constructor vacío (Obligatorio para JPA)
    public Cuadrilla() {
    }

    // Constructor completo (Ideal para tus pruebas y lógica)
    public Cuadrilla(Long id, String nombre, String zona, String especialidad, Boolean estado, List<Long> trabajadoresIds) {
        this.id = id;
        this.nombre = nombre;
        this.zona = zona;
        this.especialidad = especialidad;
        this.estado = estado;
        this.trabajadoresIds = trabajadoresIds;
    }
    //getters y setters

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

    public List<Long> getTrabajadoresIds() {
        return trabajadoresIds;
    }

    public void setTrabajadoresIds(List<Long> trabajadoresIds) {
        this.trabajadoresIds = trabajadoresIds;
    }
}
