package com.example.msespecies.controller;

import com.example.msespecies.model.Especies;
import com.example.msespecies.model.EspeciesDTO;
import com.example.msespecies.service.EspeciesService;
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

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EspeciesControllerTest {

    private MockMvc mockMvc;
    private StubEspeciesService stubService;
    private ObjectMapper objectMapper;
    private Especies especieBase;

    private static class StubEspeciesService extends EspeciesService {
        public List<Especies> listaARetornar = List.of();
        public Especies especieARetornar;
        public boolean existePorIdResultado = true;

        public StubEspeciesService() {
            super(Mockito.mock(com.example.msespecies.repository.EspeciesRepository.class));
        }

        @Override
        public List<Especies> listarEspecies() {
            return listaARetornar;
        }

        @Override
        public Especies buscarPorId(Long id) {
            return especieARetornar;
        }

        @Override
        public Boolean existePorId(Long id) {
            return existePorIdResultado;
        }

        @Override
        public Especies guardarEspecie(Especies especies) {
            return especieARetornar;
        }

        @Override
        public Optional<Especies> actualizarEspecie(Long id, Especies especies) {
            return Optional.ofNullable(especieARetornar);
        }

        @Override
        public Optional<?> actualizarParcialEspecie(Long id, EspeciesDTO dto) {
            return Optional.ofNullable(especieARetornar);
        }

        @Override
        public void eliminarEspecie(Long id) {
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubEspeciesService();
        EspeciesController controller = new EspeciesController(stubService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(mock(org.springframework.validation.Validator.class))
                .build();

        objectMapper = new ObjectMapper();

        especieBase = new Especies();
        especieBase.setId(1L);
        especieBase.setNombre("Pino Radiata");
        especieBase.setUso("Celulosa");
        especieBase.setCalidad("Alta");
        especieBase.setColor("Amarillento");
    }

    @Test
    @DisplayName("Debe listar todas las especies mediante GET")
    void debeListarTodas() throws Exception {
        stubService.listaARetornar = Arrays.asList(especieBase);

        mockMvc.perform(get("/api/especies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Pino Radiata"));
    }

    @Test
    @DisplayName("Debe obtener una especie por su ID mediante GET")
    void debeObtenerPorId() throws Exception {
        stubService.existePorIdResultado = true;
        stubService.especieARetornar = especieBase;

        mockMvc.perform(get("/api/especies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Pino Radiata"));
    }

    @Test
    @DisplayName("Debe retornar 404 en GET si la especie no existe")
    void debeRetornar404AlBuscarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(get("/api/especies/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe una especie con el ID 99"));
    }

    @Test
    @DisplayName("Debe agregar una nueva especie mediante POST")
    void debeGuardarEspecie() throws Exception {
        stubService.especieARetornar = especieBase;

        mockMvc.perform(post("/api/especies/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(especieBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe actualizar una especie existente mediante PUT")
    void debeActualizarEspecie() throws Exception {
        stubService.existePorIdResultado = true;
        stubService.especieARetornar = especieBase;

        mockMvc.perform(put("/api/especies/actualizar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(especieBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 404 en PUT si la especie no existe")
    void debeRetornar404AlActualizarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(put("/api/especies/actualizar/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(especieBase)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe una especie con el ID 99"));
    }

    @Test
    @DisplayName("Debe actualizar parcialmente una especie mediante PATCH")
    void debeActualizarParcial() throws Exception {
        stubService.especieARetornar = especieBase;
        EspeciesDTO dto = new EspeciesDTO("Nuevo Nombre", null, null, null);

        mockMvc.perform(patch("/api/especies/actualizarParcial/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 404 en PATCH si el servicio devuelve vacío")
    void debeRetornar404AlActualizarParcialInexistente() throws Exception {
        stubService.especieARetornar = null;
        EspeciesDTO dto = new EspeciesDTO("Test", null, null, null);

        mockMvc.perform(patch("/api/especies/actualizarParcial/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe eliminar una especie existente mediante DELETE")
    void debeEliminarEspecie() throws Exception {
        stubService.existePorIdResultado = true;

        mockMvc.perform(delete("/api/especies/eliminar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Especie 1 eliminada"));
    }

    @Test
    @DisplayName("Debe retornar 404 en DELETE si la especie no existe")
    void debeRetornar404AlEliminarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(delete("/api/especies/eliminar/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe una especie con la id 99"));
    }
}