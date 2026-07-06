package com.example.msfactura.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
<<<<<<< HEAD
=======

//@Entity permite a JPA detectar a esta clase para mapearla en la base de datos
>>>>>>> fix-test_y_null
@Entity
//@Table da el nombre a utilizar en la tabla de la BD
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos factura
    /* No podemos forzarlo a ser unico
    por la naturaleza de las emisiones en el SII
    una factura podria compartir numero con otra */
<<<<<<< HEAD
    @NotNull
    @Column(name = "Factura")
=======
    /*
        @NotNull se asegura que el atributo no sea nulo,
        se usa en los atributos con números
     */
    @NotNull
    // @Column da instrucciones a JPA, como el nombre de atributo y
    // que no acepte datos null en este caso
    @Column(name = "Factura", nullable = false)
    // @Schema describe y da un nombre al atributo en Swagger
>>>>>>> fix-test_y_null
    @Schema(name = "Número de  factura",
    description = "Número de la factura emitido en el SII")
    private Long numFactura;

    /*
       @NotBlank asegura que el atributo contenga por lo menos
       un caracter no vacio
     */
    // El giro es la actividad economica que paga la factura
    @NotBlank
    @Column(name = "giro",nullable = false)
    @Schema(name = "Giro",
    description = "Es el proposito del pago, pueden ser compra o pago de bienes y/o servicios")
    private String giro;

    @NotNull
    @Column(name = "monto",nullable = false)
    @Schema(name = "Monto",
    description = "Es monto total que se pago en la factura." +
            "Si bien en Chile se maneja exclusivamente montos enteros, el SII contempla " +
            "el manejo de montos en monedas extranjeras por necesidades legales, por ende, " +
            "tambien se permitira el ingreso de numeros con decimales para manejar monedas como el dolar estadounidense")
    private Double monto;

    /*
       En el caso de datos externos se asume que contienen informacion desde el
       microservicio
    */
    // Datos de Predio
    @Column(name = "direccion",nullable = false)
    private String direccion;
    @Column(name = "nombrePredio", nullable = false)
    private String nombrePredio;

    // Datos de cliente
    // La razonSocial se puede referir a una persona natural o empresa
    @Column(name = "razonSocial")
    private String razonSocial;
    @Column(name = "ciudad",nullable = false)
    private String ciudad;
    @Column(name = "comuna", nullable = false)
    private String comuna;
    @Column(name = "telefonoCliente")
    private String telefonoCliente;

    public Factura() {
    }

    public Factura(Long numFactura, String giro, Double monto) {
        this.numFactura = numFactura;
        this.giro = giro;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePredio() {
        return nombrePredio;
    }

    public void setNombrePredio(String nombrePredio) {
        this.nombrePredio = nombrePredio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public Long getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(Long numFactura) {
        this.numFactura = numFactura;
    }
}
