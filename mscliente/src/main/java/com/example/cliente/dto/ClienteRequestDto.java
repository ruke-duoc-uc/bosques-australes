package com.example.cliente.dto;
import com.example.cliente.model.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * OBJETO DE TRANSFERENCIA DE DATOS DE ENTRADA (REQUEST DTO)
 * Clase utilizada exclusivamente para capturar las peticiones de creación o actualización
 * enviadas desde el exterior (JSON) hacia los endpoints del controlador.
 * Su función principal es interceptar los datos y aplicar anotaciones de validación (Jakarta)
 * en la frontera de la aplicación, evitando que datos corruptos o incompletos lleguen a la base de datos.
 */
public class ClienteRequestDto {
    /**
     * Nombre comercial o de fantasía.
     * Validación: No permite nulos, textos vacíos ni puros espacios, con un límite estricto de 100 caracteres.
     */
    @NotBlank(message = "El nombre comercial es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    /**
     * Identificación fiscal única (RUT con formato).
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    /**
     * Nombre legal o social corporativo ante las entidades del gobierno (SII).
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    /**
     * Domicilio físico de las oficinas o dependencias principales.
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    /**
     * Comuna del domicilio corporativo del cliente.
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    /**
     * Ciudad base donde opera comercialmente la organización.
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    /**
     * Número de teléfono para canalizar la comunicación comercial.
     * Validación: Campo requerido obligatorio.
     */
    @NotBlank(message = "El teléfono de contacto es obligatorio")
    private String telefono;

    /**
     * Correo electrónico institucional.
     * Validación: Obligatorio y además debe pasar un filtro de patrón estándar para verificar que tenga un '@' y un dominio válido.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un formato de correo electrónico válido")
    private String email;

    /**
     * Rol operativo dentro del negocio forestal (CONTRATISTA, EXPORTADOR, etc.).
     * Validación: Por ser un Enum estructurado, se valida mediante @NotNull.
     */
    @NotNull(message = "El tipo de cliente es obligatorio")
    private TipoCliente tipoCliente;

    /**
     * Indicador lógico de vigencia (true = Activo, false = Inactivo).
     * Validación: Obligatorio indicar el estado booleano explícitamente en el cuerpo de la solicitud.
     */
    @NotNull(message = "El estado del cliente (true/false) es obligatorio")
    private Boolean estado;

    // --- Métodos de Acceso (Getters y Setters) ---
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
