package com.example.msacopio.model;

import jakarta.persistence.*;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Acopio")
@Schema(
        name = "Acopio",
        description = "Representa un lote de producto acopiado del proyecto Bosques Australes"
)
public class AcopioModel {

    @Schema(description = "Identificador único del acopio", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //El id y nombre  es lo que se conecta con el microoservicio de Especie
    @Schema(description = "Identificador de la especie asociada, obtenido del microservicio de Especies", example = "3")
    @Column(name = "idEspecies", nullable = false)
    private Long idEspecies;

    @Schema(description = "Nombre de la especie asociada, obtenido del microservicio de Especies", example = "Pino Radiata")
    @Column(name = "nombreEspecies", nullable = false, length = 50)
    private String nombreEspecies;

    //Datos propios de este microservicio
    @Schema(description = "Código único del producto acopiado", example = "COD-000123")
    @Column(name = "codigoProducto", nullable = false, length = 13)
    private String codigoProducto;

    @Schema(description = "Cantidad disponible del producto acopiado", example = "500")
    @Column(name = "cantidadDisponible", nullable = false)
    private Integer cantidadDisponible;

    @Schema(description = "Unidad de medida del producto acopiado", example = "KILOGRAMOS")
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;

    //el modo de ingreso de datos es yyyy-mm-dd
    @Schema(description = "Fecha de ingreso del producto al acopio (formato yyyy-mm-dd)", example = "2026-03-10")
    @Column(name = "fechaIngreso", nullable = false)
    private Date fechaIngreso;

    //Constructor vacío
    public AcopioModel() {
    }

    //Constructos con caracteres
    public AcopioModel(String codigoProducto, Integer cantidadDisponible,
                       UnidadMedida unidadMedida, Date fechaIngreso) {
        this.codigoProducto = codigoProducto;
        this.cantidadDisponible = cantidadDisponible;
        this.unidadMedida = unidadMedida;
        this.fechaIngreso = fechaIngreso;
    }

    //Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getIdEspecies() {
        return idEspecies;
    }

    public void setIdEspecies(Long idEspecies) {
        this.idEspecies = idEspecies;
    }

    public String getNombreEspecies() {
        return nombreEspecies;
    }

    public void setNombreEspecies(String nombreEspecies) {
        this.nombreEspecies = nombreEspecies;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
