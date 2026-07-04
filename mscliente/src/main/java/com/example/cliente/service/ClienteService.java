package com.example.cliente.service;

import com.example.cliente.model.*;
import com.example.cliente.repository.*;
import com.example.cliente.exception.NegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio de capa de negocio para la gestión de Clientes.
 * Contiene todas las reglas de validación y manipulación de datos antes de ir a la BD.
 */
@Service
public class ClienteService {
    // Logger para registrar eventos en la consola del servidor (Monitoreo)
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
    // Repositorio encargado de la comunicación directa con la base de datos
    protected final ClienteRepository clienteRepository;

    /**
     * Inyección por constructor del repositorio.
     */
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * ¿Qué hace?: Recupera todos los clientes registrados en el sistema.
     * ¿Qué recibe?: Nada.
     * ¿Qué entrega?: Una lista (List) con todos los objetos Cliente existentes.
     */
    public List<Cliente> listarClientes() {
        log.info("[clientes] Solicitando lista completa de clientes comerciales");
        return clienteRepository.findAll();
    }

    /**
     * ¿Qué hace?: Registra un nuevo cliente validando que el RUT no esté duplicado.
     * ¿Qué recibe?: Un objeto 'Cliente' con los datos enviados desde el controlador (JSON).
     * ¿Qué entrega?: El objeto 'Cliente' ya guardado en la base de datos con su ID autogenerado.
     * @throws NegocioException Si el RUT ya existe en el sistema (Lanza HTTP 409 Conflict).
     */
    public Cliente guardarCliente(Cliente cliente) {
        log.info("[clientes] Intentando registrar nuevo cliente: {}", cliente.getNombre());
        // REGLA DE NEGOCIO: Evitar duplicados por RUT
        if (clienteRepository.existsByRut(cliente.getRut())) {
            log.error("[clientes] Error de negocio: El RUT {} ya existe", cliente.getRut());
            throw new NegocioException("El cliente con el RUT " + cliente.getRut() + " ya se encuentra registrado.", 409);
        }

        return clienteRepository.save(cliente);
    }
    /**
     * ¿Qué hace?: Filtra los clientes según estén activos o inactivos.
     * ¿Qué recibe?: Un booleano 'estado' (true = activos, false = inactivos).
     * ¿Qué entrega?: Una lista filtrada de clientes que cumplan con esa condición.
     */
    public List<Cliente> buscarPorEstado(boolean estado) {
        log.info("[clientes] Buscando clientes con estado activo={}", estado);
        return clienteRepository.findByEstado(estado);
    }
    /**
     * ¿Qué hace?: Busca clientes cuyo nombre contenga el texto enviado ignorando mayúsculas/minúsculas.
     * ¿Qué recibe?: Un String 'nombre' con el término de búsqueda.
     * ¿Qué entrega?: Una lista de clientes que coincidan parcialmente con ese nombre.
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        log.info("[clientes] Buscando clientes por filtro de nombre: {}", nombre);
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    /**
     * ¿Qué hace?: Busca un cliente específico por su llave primaria (ID).
     * ¿Qué recibe?: El 'id' (Long) del cliente.
     * ¿Qué entrega?: El objeto 'Cliente' si lo encuentra.
     * @throws EntityNotFoundException Si el ID no existe en la base de datos (Lanza HTTP 404 Not Found).
     */
    public Cliente obtenerPorId(Long id) {
        log.info("[clientes] Buscando cliente por ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + id + " no existe en el sistema."));
    }
    /**
     * ¿Qué hace?: Modifica un cliente existente con nuevos valores.
     * ¿Qué recibe?: El 'id' (Long) del cliente a modificar y un objeto 'Cliente' con los 'datosNuevos'.
     * ¿Qué entrega?: El objeto 'Cliente' actualizado y guardado.
     */
    public Cliente actualizarCliente(Long id, Cliente datosNuevos) {
        log.info("[clientes] Modificando datos del cliente ID: {}", id);

        // CORRECCIÓN: Primero verificamos si existe, si no, salta el 404 de inmediato
        Cliente clienteExistente = obtenerPorId(id);

        // Traspasamos los nuevos valores sobre el registro existente
        clienteExistente.setNombre(datosNuevos.getNombre());
        clienteExistente.setRut(datosNuevos.getRut());
        clienteExistente.setRazonSocial(datosNuevos.getRazonSocial());
        clienteExistente.setDireccion(datosNuevos.getDireccion());
        clienteExistente.setComuna(datosNuevos.getComuna());
        clienteExistente.setCiudad(datosNuevos.getCiudad());
        clienteExistente.setTelefono(datosNuevos.getTelefono());
        clienteExistente.setEmail(datosNuevos.getEmail());
        clienteExistente.setTipoCliente(datosNuevos.getTipoCliente());
        clienteExistente.setEstado(datosNuevos.getEstado());

        return clienteRepository.save(clienteExistente);
    }

    // NUEVO REGLA DE NEGOCIO: Desactivación lógica en lugar de Delete físico
    public void desactivarCliente(Long id) {
        log.info("[clientes] Desactivando lógicamente al cliente ID: {}", id);
        Cliente cliente = obtenerPorId(id);
        cliente.setEstado(false); // Cambia el estado a inactivo
        clienteRepository.save(cliente);
    }

    // CORRECCIÓN MÉTODO DETALLE: Estructura el mapa correctamente para el controlador
    public Map<String, Object> obtenerDetalleCliente(Long id) {
        log.info("[clientes] Compilando detalle distribuido para cliente ID: {}", id);
        Cliente cliente = obtenerPorId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", cliente.getId());
        response.put("nombre", cliente.getNombre());
        response.put("rut", cliente.getRut());
        response.put("razonSocial", cliente.getRazonSocial());
        response.put("direccion", cliente.getDireccion());
        response.put("comuna", cliente.getComuna());
        response.put("ciudad", cliente.getCiudad());
        response.put("tipoCliente", cliente.getTipoCliente());
        response.put("estado", cliente.getEstado());

        // De momento lo dejamos vacío, aquí irá la llamada RestClient al micro de contratos
        //response.put("contratosAsociados", new ArrayList<>());

        return response;
    }
}
