package com.example.msfactura.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // @NotBlank se asegura que los atributos no esten vacios
    // @Schema da una breve descripcion de el contenido del atributo, junto a una explicacion de 
    // porque existe y su forma
    //Datos factura
    /* No podemos forzarlo a ser unico
    por la naturaleza de las emisiones en el SII
    una factura podria compartir numero con otra */
    @NotBlank
    // Ciertas razones sociales llegan a tener cientos de facturas
    // usualmente grandes negocios establecidos
    @Size(max=7)
    @Column(name = "Factura")
    @Schema(name = "Número de  factura",
    description = "Número de la factura emitido en el SII")
    private Long numFactura;

    // El giro es la actividad economica que paga la factura
    @NotBlank
    @Size(max = 500)
    @Column(name = "giro",nullable = false)
    @Schema(name = "Giro",
    description = "Es el proposito del pago, pueden ser compra o pago de bienes y/o servicios")
    private String giro;

    @NotBlank
    // Es posible que algunas facturas utilizen una cantidad absurda de precision con los decimales
    // en el caso de monedas extranjeras
    @Size(max=50)
    @Column(name = "monto",nullable = false)
    @Schema(name = "Monto",
    description = "Es monto total que se pago en la factura." +
            "Si bien en Chile se maneja exclusivamente montos enteros, el SII contempla " +
            "el manejo de montos en monedas extranjeras por necesidades legales, por ende, " +
            "tambien se permitira el ingreso de numeros con decimales para manejar monedas como el dolar estadounidense")
    private Double monto;
    //No se usara @Size para los atributos de otro ms para el caso de que estos cambien de tamaño
    //Datos de Predio
    @Column(name = "direccion",nullable = false)
    private String direccion;
    @Column(name = "nombrePredio", nullable = false)
    private String nombrePredio;

    //Datos de cliente
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
