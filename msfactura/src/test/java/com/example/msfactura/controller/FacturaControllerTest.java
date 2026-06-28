package com.example.msfactura.controller;

import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
import com.example.msfactura.service.FacturaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FacturaControllerTest {

    private MockMvc mockMvc;
    private StubFacturaService stubService;
    private ObjectMapper objectMapper;
    private Factura facturaBase;

    // --- STUB MANUAL ADAPTADO A RESTCLIENT ---
    private static class StubFacturaService extends FacturaService {
        public List<Factura> listaARetornar = List.of();
        public Factura facturaARetornar;
        public boolean existePorIdResultado = true;

        public StubFacturaService() {
            super(
                    Mockito.mock(com.example.msfactura.repository.FacturaRepository.class),
                    Mockito.mock(com.example.msfactura.client.PrediosClient.class),
                    Mockito.mock(com.example.msfactura.client.ClientesClient.class)
            );
        }

        @Override
        public List<Factura> listarFactura() {
            return listaARetornar;
        }

        @Override
        public Factura buscarPorId(Long id) {
            return facturaARetornar;
        }

        @Override
        public Boolean existePorId(Long id) {
            return existePorIdResultado;
        }

        @Override
        public Factura guardarFactura(Long idPredio, Long idCliente, Factura factura) {
            return facturaARetornar;
        }

        @Override
        public Optional<Factura> actualizarFacturaCompleta(Long id, Long idPredio, Long idCliente, Factura factura) {
            return Optional.ofNullable(facturaARetornar);
        }

        @Override
        public Optional<Factura> actualizarFacturaParcial(Long id, FacturaDTO facturaDTO) {
            return Optional.ofNullable(facturaARetornar);
        }

        @Override
        public void eliminarFactura(Long id) {}
    }

    @BeforeEach
    void setUp() {
        stubService = new StubFacturaService();
        FacturaController controller = new FacturaController(stubService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(mock(org.springframework.validation.Validator.class))
                .build();

        objectMapper = new ObjectMapper();

        facturaBase = new Factura();
        facturaBase.setId(1L);
        facturaBase.setNumFactura(9999L);
        facturaBase.setGiro("Construccion");
        facturaBase.setMonto(890000.0);
    }

    @Test
    @DisplayName("GET /api/factura - Debe listar todas las facturas")
    void debeListarFacturas() throws Exception {
        stubService.listaARetornar = Arrays.asList(facturaBase);

        mockMvc.perform(get("/api/factura")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].numFactura").value(9999));
    }

    @Test
    @DisplayName("GET /api/factura/{id} - Debe obtener una factura si existe")
    void debeObtenerPorId() throws Exception {
        stubService.existePorIdResultado = true;
        stubService.facturaARetornar = facturaBase;

        mockMvc.perform(get("/api/factura/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numFactura").value(9999));
    }

    @Test
    @DisplayName("GET /api/factura/{id} - Debe retornar 404 si la factura no existe")
    void debeRetornar404AlBuscarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(get("/api/factura/{id}", 88L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La factura con id 88 no fue encontrada, intente de nuevo"));
    }

    @Test
    @DisplayName("POST /api/factura/guardar/{idPredio}/{idCliente} - Debe guardar exitosamente")
    void debeGuardarFactura() throws Exception {
        stubService.facturaARetornar = facturaBase;

        mockMvc.perform(post("/api/factura/guardar/{idPredio}/{idCliente}", 10L, 20L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facturaBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/factura/actualizar/{id}/{idPredio}/{idCliente} - Debe procesar la actualización completa")
    void debeActualizarCompleta() throws Exception {
        stubService.facturaARetornar = facturaBase;

        mockMvc.perform(put("/api/factura/actualizar/{id}/{idPredio}/{idCliente}", 1L, 10L, 20L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facturaBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/factura/actualizarParcial/{id} - Debe recibir FacturaDTO de manera correcta")
    void debeActualizarParcial() throws Exception {
        stubService.facturaARetornar = facturaBase;
        FacturaDTO dto = new FacturaDTO(10L, 20L, 777L, "Giro Comercial", 15000.0);

        mockMvc.perform(patch("/api/factura/actualizarParcial/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/factura/eliminar/{id} - Debe eliminar si el ID existe")
    void debeEliminarFactura() throws Exception {
        stubService.existePorIdResultado = true;

        mockMvc.perform(delete("/api/factura/eliminar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Factura 1 eliminada"));
    }

    @Test
    @DisplayName("DELETE /api/factura/eliminar/{id} - Debe responder 404 si el ID no existe")
    void debeRetornar404AlEliminarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(delete("/api/factura/eliminar/{id}", 55L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La id 55 no existe"));
    }
}