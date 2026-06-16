package com.example.msacopio.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Acopio")
public class AcopioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //El id y nombre  es lo que se conecta con el microoservicio de Especie
    @Column(name = "idEspecies", nullable = false)
    private Long idEspecies;
    @Column(name = "nombreEspecies", nullable = false, length = 50)
    private String nombreEspecies;

    //Datos propios de este microservicio
    @Column(name = "codigoProducto", nullable = false, length = 13)
    private String codigoProducto;
    @Column(name = "cantidadDisponible", nullable = false)
    private Integer cantidadDisponible;
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;
    //el modo de ingreso de datos es yyyy-mm-dd
    @Column(name = "fechaIngreso", nullable = false)
    private Date fechaIngreso;


    public AcopioModel() {
    }

    public AcopioModel(String codigoProducto, Integer cantidadDisponible,
                       UnidadMedida unidadMedida, Date fechaIngreso) {
        this.codigoProducto = codigoProducto;
        this.cantidadDisponible = cantidadDisponible;
        this.unidadMedida = unidadMedida;
        this.fechaIngreso = fechaIngreso;
    }

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
