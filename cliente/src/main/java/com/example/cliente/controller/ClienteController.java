package com.example.cliente.controller;
import com.example.cliente.model.*;
import com.example.cliente.repository.*;
import com.example.cliente.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<?> listaClientes() {
        try {
            List<Cliente> clientes = clienteService.listarClientes();
            if (clientes.isEmpty()) {
                // Si no hay errores pero la lista está vacía, devolvemos 204 No Content
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            // Si algo sale mal (ej. la base de datos está caída), devolvemos 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de clientes: " + e.getMessage());
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<Cliente> clientes = clienteService.buscarPorNombre(nombre);
            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron clientes con el nombre: " + nombre);
            }
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(@PathVariable boolean estado) {
        try {
            return ResponseEntity.ok(clienteService.buscarPorActividad(estado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            // Buscamos al cliente por su ID
            Cliente cliente = clienteService.obtenerPorId(id);

            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                // Si el servicio devuelve null, respondemos con 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: El cliente con ID " + id + " no existe en la base de datos.");
            }
        } catch (Exception e) {
            // Si hay un error de conexión o de base de datos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la búsqueda: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
        try {
            Cliente guardado = clienteService.guardarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente detalles) {
        try {
            Cliente clienteExistente = clienteService.obtenerPorId(id);
            if (clienteExistente == null) return ResponseEntity.notFound().build();

            clienteExistente.setNombre(detalles.getNombre());
            clienteExistente.setGiro(detalles.getGiro());

            return ResponseEntity.ok(clienteService.guardarCliente(clienteExistente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar: " + e.getMessage());
        }
    }
}
