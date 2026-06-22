package com.example.cliente.controller;

import com.example.cliente.dto.ClienteRequestDto;
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
    private ClienteRequestDto requestDtoBase;

    // CORRECCIÓN 1: Cambiamos 'implements' por 'extends' porque ClienteService es una clase regular
    private static class StubClienteService extends ClienteService {
        public List<Cliente> listaAretornar = new ArrayList<>();
        public Cliente clienteGuardado;
        public Cliente clienteActualizado;
        public Map<String, Object> detalleAretornar = new HashMap<>();

        // CORRECCIÓN 2: Constructor del stub para neutralizar las dependencias originales (Repository, Mapper, etc.)
        public StubClienteService() {
            super(null, null); // Pasa tantos 'null' como parámetros pida tu constructor de ClienteService original
        }

        @Override
        public List<Cliente> listarClientes() {
            return listaAretornar;
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
            // No requiere lógica interna para este flujo
        }

        @Override
        public Map<String, Object> obtenerDetalleCliente(Long id) {
            return detalleAretornar;
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubClienteService();
        ClienteController clienteController = new ClienteController(stubService);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();

        clienteBase = new Cliente(
                "Forestal Valdivia S.A.", "77.345.678-9",
                "Sociedad Comercial Forestal Valdivia Limitada", "Esmeralda 450",
                "Valdivia", "Valdivia", "+56632221100",
                "finanzas@forvaldivia.cl", TipoCliente.EXPORTADOR, true
        );
        clienteBase.setId(1L);

        requestDtoBase = new ClienteRequestDto();
        requestDtoBase.setNombre("Forestal Valdivia S.A.");
        requestDtoBase.setRut("77.345.678-9");
        requestDtoBase.setRazonSocial("Sociedad Comercial Forestal Valdivia Limitada");
        requestDtoBase.setDireccion("Esmeralda 450");
        requestDtoBase.setComuna("Valdivia");
        requestDtoBase.setCiudad("Valdivia");
        requestDtoBase.setTelefono("+56632221100");
        requestDtoBase.setEmail("finanzas@forvaldivia.cl");
        requestDtoBase.setTipoCliente(TipoCliente.EXPORTADOR);
        requestDtoBase.setEstado(true);
    }

    @Test
    @DisplayName("Debe retornar 200 OK con la lista de clientes si existen registros")
    void debeRetornarListaClientes() throws Exception {
        stubService.listaAretornar = Arrays.asList(clienteBase);

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Forestal Valdivia S.A."));
    }

    @Test
    @DisplayName("Debe retornar 204 No Content si la lista está vacía")
    void debeRetornarNoContent() throws Exception {
        stubService.listaAretornar = new ArrayList<>();

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 201 Created al registrar exitosamente")
    void debeCrearCliente() throws Exception {
        stubService.clienteGuardado = clienteBase;

        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoBase)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 200 OK al actualizar con éxito")
    void debeActualizarCliente() throws Exception {
        stubService.clienteActualizado = clienteBase;

        mockMvc.perform(put("/api/cliente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoBase)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 204 No Content al desactivar lógicamente")
    void debeDesactivarCliente() throws Exception {
        mockMvc.perform(patch("/api/cliente/{id}/desactivar", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 OK con el mapa del detalle completo")
    void debeObtenerDetalle() throws Exception {
        Map<String, Object> detalleSimulado = new HashMap<>();
        detalleSimulado.put("id", 1L);
        detalleSimulado.put("nombre", "Forestal Valdivia S.A.");
        stubService.detalleAretornar = detalleSimulado;

        mockMvc.perform(get("/api/cliente/{id}/detalle", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}