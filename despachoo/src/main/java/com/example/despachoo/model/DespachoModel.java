package com.example.despachoo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un Despacho dentro del microservicio.
 * Cada instanciade esta clase se mapea a una fila de la tabla "Despacho" en la base de datos.
 */

@Entity //Le indica a JPA que esta es una entidad persistente (se mapea a un a tabla)
@Table(
        name = "Despacho" //Define explicitamente el nombre de la tabla en la base de datos.
)
public class DespachoModel {

    //Identificador unico del despacho
    @Id //Marca este campo como llave primaria (Primary key) en la tabla.
    @GeneratedValue(
            strategy = GenerationType.IDENTITY //El valor del ID lo genera automáticamente la base de datos (auto-incremental).
    )
    private Long id;
    //Nombre de la persona que realiza el despacho.
    @Column(
            name = "nombreDespachador",
            nullable = false, //No se permite guardar un despacho sin nombre de despachador.
            length = 100 //Largo máximo de 100 caracteres en la columna varchar.
    )
    private String nombreDespachador;

    //Estado actual del desapcho (Ejemplo: GENERADA, TRANSITO, ENTREGADA Y CANCELADA)
    @Column(
            name = "estado",
            nullable = false)
    @Enumerated(EnumType.STRING) //Guarda el enum como texto (EJ: GENERADA) en lugar de un número (ordinal).
    private estado estado;

    //Lugar donde se recibirá el pedido (Ejemplo: PREDIOS, BODEGA Y OFICINA)
    @Column(
            name = "lugarRecepcion",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private lugarRecepcion lugarRecepcion;

    //Tipo de pedido asociado al despacho (Ejemplo: EXPORTACION)
    @Column(
            name = "tipoPedido",
            nullable = false,
            length = 15
    )
    @Enumerated(EnumType.STRING)
    private tipoPedido tipoPedido;

    //Localidad física asociada al despacho
    @Column(
            name = "Localidad",
            nullable = false
    )
    private String localidad;

    //Número de factura vinculada a este despacho (Se obtiene desde el microservicio de facturas)
    @Column(
            name = "factura",
            nullable = false,
            length = 25
    )
    private Long factura;

    //Texto que describe la trazabilidad completa del pedido (historial/ruta)
    @Column(
            name = "trazabilidadCompleta",
            nullable = false
    )
    private String trazabilidadCompleta;

    //Nombre de la especie forestal asociada (se obtiene desde el microservio de especies)
    @Column(
            name = "especie",
            nullable = false
    )
    private String especie;

    //Constructor vacío requerido por JPA para poder instanciar la entidad por la reflexión.
    public DespachoModel() {
    }

    //Getters and Setters: permiten leer y modificar cada atributo de forma controlada.
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDespachador() {
        return this.nombreDespachador;
    }

    public void setNombreDespachador(String nombreDespachador) {
        this.nombreDespachador = nombreDespachador;
    }

    public estado getEstado() {
        return this.estado;
    }

    public void setEstado(estado estado) {
        this.estado = estado;
    }

    public lugarRecepcion getLugarRecepcion() {
        return this.lugarRecepcion;
    }

    public void setLugarRecepcion(lugarRecepcion lugarRecepcion) {
        this.lugarRecepcion = lugarRecepcion;
    }

    public tipoPedido getTipoPedido() {
        return this.tipoPedido;
    }

    public void setTipoPedido(tipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getFactura() {
        return this.factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public String getTrazabilidadCompleta() {
        return this.trazabilidadCompleta;
    }

    public void setTrazabilidadCompleta(String trazabilidadCompleta) {
        this.trazabilidadCompleta = trazabilidadCompleta;
    }

    public String getEspecie() {
        return this.especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    //Constructor con todos los atributos (util para crear un despacho ya "armado" sin necesidad del id,
    // ya que este lo genera la base de datos automáticamente).
    public DespachoModel(String nombreDespachador, estado estado, lugarRecepcion lugarRecepcion, tipoPedido tipoPedido, String localidad, Long factura, String trazabilidadCompleta, String especie) {
        this.nombreDespachador = nombreDespachador;
        this.estado = estado;
        this.lugarRecepcion = lugarRecepcion;
        this.tipoPedido = tipoPedido;
        this.localidad = localidad;
        this.factura = factura;
        this.trazabilidadCompleta = trazabilidadCompleta;
        this.especie = especie;
    }
}
