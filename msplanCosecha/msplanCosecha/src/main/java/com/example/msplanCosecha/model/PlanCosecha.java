package com.example.msplanCosecha.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(name = "Plan de Cosecha",description = "Cada plan de cosecha explica como cuales son los procesos asociados a la cosecha de un rodal, y que caracteristicas deben tener")
@Table(name = "planCosecha")
public class PlanCosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Datos PlanCosecha
    @Column(name = "edadRodal")
    @Schema(name = "Edad de rodal",description = "Es el rango de edad aproximado que del rodal")
    private Long edadRodal;
    @Schema(name = "Altura promedio de rodal", description = "Es la altura promedio del rodal")
    @Column(name = "alturaPromedio")
    private Double alturaPromedio;
    @Schema(name = "",description = "Aqui se dan todos los detalles, sobre el procedimiento de cosecha, herramientas, tecnicas, medidas de seguridad y otros detalles importantes")
    @Column(name = "descripcion")
    private String descripcion;
    //Datos especie
    @Column(name = "especie")
    private String especie;
    public PlanCosecha() {
    }

    public PlanCosecha(Long edadRodal, String descripcion, Double alturaPromedio) {
        this.edadRodal = edadRodal;
        this.descripcion = descripcion;
        this.alturaPromedio = alturaPromedio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEdadRodal() {
        return edadRodal;
    }

    public void setEdadRodal(Long edadRodal) {
        this.edadRodal = edadRodal;
    }

    public Double getAlturaPromedio() {
        return alturaPromedio;
    }

    public void setAlturaPromedio(Double alturaPromedio) {
        this.alturaPromedio = alturaPromedio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
