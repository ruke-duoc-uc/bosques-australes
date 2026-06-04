package com.example.cliente.service;

import com.example.cliente.model.*;
import com.example.cliente.repository.*;
import com.example.seguridad.exception.NegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ClienteService {
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        log.info("[clientes] Solicitando lista completa de clientes comerciales");
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) {
        log.info("[clientes] Intentando registrar nuevo cliente: {}", cliente.getNombre());

        // REGLA DE NEGOCIO: No permitir RUTs duplicados en BosquesAustrales
        if (clienteRepository.existsByRut(cliente.getRut())) {
            log.error("[clientes] Error de negocio: El RUT {} ya existe", cliente.getRut());
            throw new NegocioException("El cliente con el RUT " + cliente.getRut() + " ya se encuentra registrado.", 409);
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> buscarPorEstado(boolean estado) {
        log.info("[clientes] Buscando clientes con estado activo={}", estado);
        return clienteRepository.findByEstado(estado);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        log.info("[clientes] Buscando clientes por filtro de nombre: {}", nombre);
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Cliente obtenerPorId(Long id) {
        log.info("[clientes] Buscando cliente por ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + id + " no existe en el sistema."));
    }

    public Cliente actualizarCliente(Cliente cliente) {
        log.info("[clientes] Modificando datos del cliente ID: {}", cliente.getId());
        return clienteRepository.save(cliente);
    }
}
