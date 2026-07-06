package com.example.msplanCosecha.controller;

import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.model.PlanCosechaDTO;
import com.example.msplanCosecha.service.PlanCosechaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlanCosechaControllerTest {

    private MockMvc mockMvc;
    private StubPlanCosechaService stubService;
    private ObjectMapper objectMapper;
    private PlanCosecha planBase;

    private static class StubPlanCosechaService extends PlanCosechaService {
        public List<PlanCosecha> listaARetornar = List.of();
        public PlanCosecha planARetornar;
        public boolean existePorIdResultado = true;

        public StubPlanCosechaService() {
            super(Mockito.mock(com.example.msplanCosecha.repository.PlanCosechaRepository.class),
                    Mockito.mock(com.example.msplanCosecha.client.EspeciesClient.class));
        }

        @Override
        public List<PlanCosecha> listarPlanCosecha() {
            return listaARetornar;
        }

        @Override
        public PlanCosecha obtenerPorId(Long id) {
            return planARetornar;
        }

        @Override
        public Boolean existePorid(Long id) {
            return existePorIdResultado;
        }

        @Override
        public PlanCosecha guardarPlanCosecha(Long idEspecie, PlanCosecha planCosecha) {
            return planARetornar;
        }

        @Override
        public Optional<PlanCosecha> actualizarPlanCompleto(Long id, Long idEspecie, PlanCosecha planCosecha) {
            return Optional.ofNullable(planARetornar);
        }

        @Override
        public Optional<PlanCosecha> actualizarPlanCosecha(Long id, PlanCosechaDTO dto) {
            return Optional.ofNullable(planARetornar);
        }

        @Override
        public void eliminarPorId(Long id) {
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubPlanCosechaService();
        PlanCosechaController controller = new PlanCosechaController(stubService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(mock(org.springframework.validation.Validator.class))
                .build();

        objectMapper = new ObjectMapper();

        planBase = new PlanCosecha();
        planBase.setId(1L);
        planBase.setAlturaPromedio(28.4);
        planBase.setEdadRodal(12L);
        planBase.setDescripcion("Zona Sur");
        planBase.setEspecie("Pino");
    }

    @Test
    @DisplayName("Debe listar todos los planes mediante GET")
    void debeListarTodos() throws Exception {
        stubService.listaARetornar = Arrays.asList(planBase);

        mockMvc.perform(get("/api/planCosecha")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].descripcion").value("Zona Sur"));
    }

    @Test
    @DisplayName("Debe obtener un plan por su ID mediante GET")
    void debeObtenerPorId() throws Exception {
        stubService.existePorIdResultado = true;
        stubService.planARetornar = planBase;

        mockMvc.perform(get("/api/planCosecha/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Zona Sur"));
    }

    @Test
    @DisplayName("Debe retornar 404 en GET si el plan no existe")
    void debeRetornar404AlBuscarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(get("/api/planCosecha/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("La id 99 no existe")));
    }

    @Test
    @DisplayName("Debe guardar un nuevo plan mediante POST")
    void debeGuardarPlanCosecha() throws Exception {
        stubService.planARetornar = planBase;

        mockMvc.perform(post("/api/planCosecha/guardar/{idEspecie}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe actualizar un plan completo mediante PUT")
    void debeActualizarPlanCompleto() throws Exception {
        stubService.planARetornar = planBase;

        mockMvc.perform(put("/api/planCosecha/actualizarCompleto/{id}/{idEspecie}", 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe actualizar parcialmente un plan mediante PATCH")
    void debeActualizarParcial() throws Exception {
        stubService.planARetornar = planBase;
        PlanCosechaDTO dto = new PlanCosechaDTO(null, 30.0, null, null);

        mockMvc.perform(patch("/api/planCosecha/actualizarParcial/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 404 en PATCH si el servicio devuelve vacío")
    void debeRetornar404AlActualizarParcialInexistente() throws Exception {
        stubService.planARetornar = null;
        PlanCosechaDTO dto = new PlanCosechaDTO(null, 30.0, null, null);

        mockMvc.perform(patch("/api/planCosecha/actualizarParcial/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe eliminar un plan existente mediante DELETE")
    void debeEliminarPlanCosecha() throws Exception {
        stubService.existePorIdResultado = true;

        mockMvc.perform(delete("/api/planCosecha/eliminar")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Plan de Cosecha 1 eliminada"));
    }

    @Test
    @DisplayName("Debe retornar 404 en DELETE si el plan no existe")
    void debeRetornar404AlEliminarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(delete("/api/planCosecha/eliminar")
                        .param("id", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("La id 99 no existe")));
    }
}