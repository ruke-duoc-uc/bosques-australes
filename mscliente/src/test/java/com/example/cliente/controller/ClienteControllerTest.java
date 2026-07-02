package com.example.cliente.controller;

import com.example.cliente.model.Cliente;
import com.example.cliente.model.TipoCliente;
import com.example.cliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClienteControllerTest {

    private MockMvc mockMvc;
    private StubClienteService stubService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Cliente clienteBase;

    // Stub hecho a mano para saltarse las restricciones de Mockito en Java 26
    private static class StubClienteService extends ClienteService {
        public List<Cliente> listaARetornar = new ArrayList<>();
        public Cliente clienteGuardado;
        public Cliente clienteActualizado;
        public Map<String, Object> detalleARetornar = new HashMap<>();

        public StubClienteService() {
            super(null); // Pasa null al repositorio para no levantar dependencias reales
        }

        @Override
        public List<Cliente> listarClientes() {
            return listaARetornar;
        }

        @Override
        public Cliente guardarCliente(Cliente cliente) {
            return clienteGuardado;
        }

        @Override
        public Cliente actualizarCliente(Long id, Cliente cliente) {
            return clienteActualizado;
        }

        @Override
        public void desactivarCliente(Long id) {
            // No hace nada, simula éxito directo
        }

        @Override
        public Map<String, Object> obtenerDetalleCliente(Long id) {
            return detalleARetornar;
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubClienteService();
        ClienteController controller = new ClienteController(stubService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        clienteBase = new Cliente(
                "Forestal Valdivia S.A.", "77.345.678-9",
                "Sociedad Comercial Forestal Valdivia Limitada", "Esmeralda 450",
                "Valdivia", "Valdivia", "+56632221100",
                "finanzas@forvaldivia.cl", TipoCliente.EXPORTADOR, true
        );
        clienteBase.setId(1L);
    }

    @Test
    @DisplayName("Debe retornar 200 OK con la lista de clientes")
    void debeRetornarListaClientes() throws Exception {
        stubService.listaARetornar = Arrays.asList(clienteBase);

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Forestal Valdivia S.A."));
    }

    @Test
    @DisplayName("Debe retornar 204 No Content si la lista está vacía")
    void debeRetornarNoContent() throws Exception {
        stubService.listaARetornar = new ArrayList<>();

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 201 Created al guardar un nuevo cliente")
    void debeGuardarCliente() throws Exception {
        stubService.clienteGuardado = clienteBase;

        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteBase)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 200 OK al actualizar un cliente existente")
    void debeActualizarCliente() throws Exception {
        stubService.clienteActualizado = clienteBase;

        mockMvc.perform(put("/api/cliente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteBase)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 204 No Content al desactivar un cliente")
    void debeDesactivarCliente() throws Exception {
        // Cambiamos 'delete' por 'patch' y añadimos '/desactivar' a la URL
        mockMvc.perform(patch("/api/cliente/{id}/desactivar", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 OK con el mapa de detalle del cliente")
    void debeObtenerDetalleCliente() throws Exception {
        Map<String, Object> detalleSimulado = new HashMap<>();
        detalleSimulado.put("id", 1L);
        detalleSimulado.put("nombre", "Forestal Valdivia S.A.");
        stubService.detalleARetornar = detalleSimulado;

        mockMvc.perform(get("/api/cliente/{id}/detalle", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Forestal Valdivia S.A."));
    }
}