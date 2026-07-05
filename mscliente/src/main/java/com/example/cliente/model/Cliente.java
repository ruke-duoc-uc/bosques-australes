package com.example.cliente.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 * ENTIDAD DE DOMINIO (MODELO JPA)
 * * Representa físicamente la tabla "clientes" en la base de datos.
 * Mapea cada columna del motor relacional con los atributos de Java mediante anotaciones @Entity.
 * Es el reflejo directo de la estructura de datos que almacena el sistema.
 */
@Entity

@Table(name = "cliente")
@Schema(
        name = "Cliente",
        description = "Entidad del modelo que representa un cliente en BosquesAustrales"
)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Identificador único del cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    /**
     * Nombre o denominación corta asignada al cliente comercial.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre corta del cliente", example = "Forestal Valdivia S.A.")
    private String nombre;

    /**
     * RUT de la organización o persona. Funciona como clave única fiscal.
     */
    @Column(name = "rut", nullable = false, length = 100)
    @NotBlank(message = "El RUT es obligatorio")
    @Schema(description = "RUT corporativo con guión y dígito verificador", example = "77.345.678-9")
    private String rut;

    /**
     * Razón Social legal e impositiva de la empresa registrada ante el Servicio de Impuestos Internos (SII).
     */
    @Column(name = "razonSocial", nullable = false)
    @Schema(description = "Razón social legal registrada ante el SII", example = "Sociedad Comercial Forestal Valdivia Limitada")
    private String razonSocial;

    /**
     * Dirección física de la casa matriz o domicilio principal de operaciones.
     */
    @Column(name = "direccion", nullable = false)
    @NotBlank(message = "La dirección es obligatoria")
    @Schema(description = "Dirección de la casa o empresa", example = "Esmeralda 450")
    private String direccion;

    /**
     * Comuna donde se emplaza el domicilio del cliente.
     */
    @Column(name = "comuna", nullable = false)
    @NotBlank(message = "La comuna es obligatoria")
    @Schema(description = "Comuna de ubicación", example = "Valdivia")
    private String comuna;

    /**
     * Ciudad o región geográfica principal de operación del cliente comercial.
     */
    @Column(name = "ciudad", nullable = false)
    @NotBlank(message = "La ciudad es obligatoria")
    @Schema(description = "Ciudad de operación principal", example = "Valdivia")
    private String ciudad;

    /**
     * Número telefónico de red fija o móvil para contactos comerciales o de cobranza.
     */
    @Column(name = "telefono", nullable = false)
    @Schema(description = "Teléfono de contacto comercial", example = "+56632221100")
    private String telefono;

    /**
     * Correo electrónico institucional del cliente destinado al envío de facturas y avisos legales.
     */
    @Column(name = "email", nullable = false)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser una dirección de correo válida")
    @Schema(description = "Correo electrónico para facturación", example = "finanzas@forvaldivia.cl")
    private String email;

    /**
     * Clasificación o rol que cumple el cliente dentro del negocio forestal (Enum).
     */
    @Column(name = "tipoCliente", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de cliente es obligatorio")
    @Schema(description = "Categoría del cliente forestal", example = "CONTRATISTA", allowableValues = {"EXPORTADOR", "PLANTA_PELLETS", "CONSTRUCTORA"})
    private TipoCliente tipoCliente;

    /**
     * Estado operativo lógico del cliente en el software.
     * true = Cuenta habilitada para transacciones comerciales.
     * false = Desactivado por borrado lógico (Conserva historial relacional).
     */
    @Column(name = "estado", nullable = false)
    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Indica si el cliente está activo", example = "true", defaultValue = "true")
    private Boolean estado;

    //constructor vacio
    public Cliente() {
    }

    //constructor con argumentos
    public Cliente(String nombre, String rut, String razonSocial, String direccion, String comuna, String ciudad, String telefono, String email, TipoCliente tipoCliente, Boolean estado) {
        this.nombre = nombre;
        this.rut = rut;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.comuna = comuna;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.email = email;
        this.tipoCliente = tipoCliente;
        this.estado = estado;
    }

    //getter y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}