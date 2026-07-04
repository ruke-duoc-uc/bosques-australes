package com.example.cliente.controller;

import com.example.cliente.dto.ClienteRequestDto;
import com.example.cliente.dto.ClienteResponseDto;
import com.example.cliente.model.Cliente;
import com.example.cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controlador REST que expone los endpoints HTTP para gestionar Clientes.
 * Url base de acceso: http://localhost:8081/api/cliente
 */
@RestController
@RequestMapping("/api/cliente")
@Tag(
        name = "Clientes",
        description = "Operaciones comerciales para la administración de clientes de BosquesAustrales"
)
public class ClienteController {

    private final ClienteService clienteService;
    /**
     * Inyección por constructor del servicio de negocio.
     */

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    /**
     * ¿Qué hace?: Atiende peticiones HTTP GET para listar todos los clientes.
     * ¿Qué recibe?: Nada.
     * ¿Qué entrega?:
     * - HTTP 200 OK: Lista de objetos 'ClienteResponseDto' (Datos públicos filtrados).
     * - HTTP 204 No Content: Si la lista en la BD está vacía.
     */
    @Operation(
            summary = "Obtiene todos los clientes",
            description = "Retorna una lista con todos los clientes comerciales registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "No hay clientes registrados en el sistema"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getAll() {
        List<ClienteResponseDto> dtos = clienteService.listarClientes().stream()
                .map(this::convertirAResponseDto)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dtos);
    }

    /**
     * ¿Qué hace?: Busca un cliente individual mediante la variable de ruta {id}.
     * ¿Qué recibe?: El id (Long) en la URL (ej: /api/cliente/5).
     * ¿Qué entrega?:
     * - HTTP 200 OK: El DTO del cliente encontrado.
     * - HTTP 404 Not Found: Si el servicio lanza una excepción al no hallar el ID.
     */
    @Operation(
            summary = "Obtiene un cliente por su ID",
            description = "Retorna los datos de un cliente comercial registrado en el sistema buscando por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado con el ID especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(convertirAResponseDto(cliente));
    }
    /**
     * ¿Qué hace?: Procesa la creación de un nuevo cliente.
     * ¿Qué recibe?: Un JSON en el cuerpo (@RequestBody) mapeado como ClienteRequestDto.
     * La anotación @Valid activa las validaciones del DTO (como @NotNull, @Email, etc.).
     * ¿Qué entrega?:
     * - HTTP 201 Created: El cliente recién guardado con su ID autogenerado.
     * - HTTP 400 Bad Request: Si las validaciones fallan.
     * - HTTP 409 Conflict: Si el RUT ya existe (controlado desde el Service).
     */
    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Permite ingresar un nuevo cliente comercial validando que el RUT no esté duplicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o campos obligatorios faltantes"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El RUT ingresado ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @PostMapping("/guardar")
    public ResponseEntity<ClienteResponseDto> create(@Valid @RequestBody ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        mapearDtoAEntidad(dto, cliente);

        Cliente guardado = clienteService.guardarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAResponseDto(guardado));
    }
    /**
     * ¿Qué hace?: Sobreescribe los datos de un cliente existente por ID.
     * ¿Qué recibe?: El id en la ruta y el nuevo JSON con modificaciones en el cuerpo (@RequestBody).
     * ¿Qué entrega?: HTTP 200 OK con el DTO del cliente ya modificado.
     */
    @Operation(
            summary = "Actualizar datos de un cliente",
            description = "Modifica la información de un cliente existente buscando por su Identificador único"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos modificados no válidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado con el ID especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> update(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto dto) {
        Cliente datosNuevos = new Cliente();
        mapearDtoAEntidad(dto, datosNuevos);

        // CORRECCIÓN: Pasamos el ID y el objeto de datos modificados correctamente
        Cliente actualizado = clienteService.actualizarCliente(id, datosNuevos);
        return ResponseEntity.ok(convertirAResponseDto(actualizado));
    }
    /**
     * ¿Qué hace?: Aplica una actualización parcial usando PATCH para desactivar la cuenta del cliente (Borrado Lógico).
     * ¿Qué recibe?: El id en la ruta (ej: /api/cliente/5/desactivar).
     * ¿Qué entrega?: HTTP 204 No Content para indicar éxito sin enviar datos de respuesta.
     */
    @Operation(
            summary = "Desactivar un cliente (Eliminación Lógica)",
            description = "Cambia el estado del cliente a falso (inactivo) para resguardar la integridad de los datos históricos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        clienteService.desactivarCliente(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * ¿Qué hace?: Compila una vista estructurada en formato clave-valor (Map) del cliente.
     * ¿Qué recibe?: El id en la ruta.
     * ¿Qué entrega?: HTTP 200 OK con el mapa de datos que incluye información que se cruzará con el microservicio de contratos.
     */
    @Operation(
            summary = "Obtener el detalle de un cliente con sus contratos",
            description = "Consulta la información local del cliente y se conecta de forma distribuida con el módulo de Contratos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle comercial compilado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID del cliente no existe")
    })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> getDetalle(@PathVariable Long id) {
        // CORRECCIÓN: Ahora llama al método correcto que genera el Map
        Map<String, Object> detalle = clienteService.obtenerDetalleCliente(id);
        return ResponseEntity.ok(detalle);
    }

    // --- Métodos Auxiliares de Traspaso ---
    /**
     * Pasa los datos desde una Entidad origen (JPA / BD) hacia un DTO destino (Respuesta JSON externa).
     */
    private ClienteResponseDto convertirAResponseDto(Cliente entidad) {
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setRut(entidad.getRut());
        dto.setRazonSocial(entidad.getRazonSocial());
        dto.setDireccion(entidad.getDireccion());
        dto.setComuna(entidad.getComuna());
        dto.setCiudad(entidad.getCiudad());
        dto.setTelefono(entidad.getTelefono());
        dto.setEmail(entidad.getEmail());
        dto.setTipoCliente(entidad.getTipoCliente());
        dto.setEstado(entidad.getEstado());
        return dto;
    }

    /**
     * Pasa los datos desde un DTO de entrada (Request JSON) hacia una Entidad JPA lista para ser procesada.
     */
    private void mapearDtoAEntidad(ClienteRequestDto dto, Cliente entidad) {
        entidad.setNombre(dto.getNombre());
        entidad.setRut(dto.getRut());
        entidad.setRazonSocial(dto.getRazonSocial());
        entidad.setDireccion(dto.getDireccion());
        entidad.setComuna(dto.getComuna());
        entidad.setCiudad(dto.getCiudad());
        entidad.setTelefono(dto.getTelefono());
        entidad.setEmail(dto.getEmail());
        entidad.setTipoCliente(dto.getTipoCliente());
        entidad.setEstado(dto.getEstado());
    }
}