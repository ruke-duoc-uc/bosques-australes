package com.example.mspredios.controller;

import com.example.mspredios.model.Predios;
import com.example.mspredios.model.PrediosDTO;
import com.example.mspredios.service.PrediosService;
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

public class PrediosControllerTest {

    private MockMvc mockMvc;
    private StubPrediosService stubService;
    private ObjectMapper objectMapper;
    private Predios predioBase;

    private static class StubPrediosService extends PrediosService {
        public List<Predios> listaARetornar = List.of();
        public Predios predioARetornar;
        public boolean existePorIdResultado = true;

        public StubPrediosService() {
            super(Mockito.mock(com.example.mspredios.repository.PrediosRepository.class));
        }

        @Override
        public List<Predios> listarPredios() {
            return listaARetornar;
        }

        @Override
        public Predios buscarPorId(Long id) {
            return predioARetornar;
        }

        @Override
        public Boolean existePorId(Long id) {
            return existePorIdResultado;
        }

        @Override
        public Predios guardarPredio(Predios predios) {
            return predioARetornar;
        }

        @Override
        public Optional<Predios> actualizarPredio(Long id, Predios predios) {
            return Optional.ofNullable(predioARetornar);
        }

        @Override
        public Optional<?> actualizarParcialPredios(Long id, PrediosDTO prediosDTO) {
            return Optional.ofNullable(predioARetornar);
        }

        @Override
        public void eliminarPredio(Long id) {
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubPrediosService();
        PrediosController controller = new PrediosController(stubService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(mock(org.springframework.validation.Validator.class))
                .build();

        objectMapper = new ObjectMapper();

        predioBase = new Predios();
        predioBase.setId(1L);
        predioBase.setNombre("Predio Bosque Austral");
        predioBase.setCiudad("Valdivia");
        predioBase.setComuna("Los Lagos");
        predioBase.setDireccion("Ruta T-35 KM 12");
    }

    @Test
    @DisplayName("Debe listar todos los predios mediante GET")
    void debeListarTodos() throws Exception {
        stubService.listaARetornar = Arrays.asList(predioBase);

        mockMvc.perform(get("/api/predios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Predio Bosque Austral"));
    }

    @Test
    @DisplayName("Debe obtener un predio por su ID mediante GET")
    void debeObtenerPorId() throws Exception {
        stubService.predioARetornar = predioBase;

        mockMvc.perform(get("/api/predios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Predio Bosque Austral"));
    }

    @Test
    @DisplayName("Debe guardar un nuevo predio mediante POST")
    void debeGuardarPredio() throws Exception {
        stubService.predioARetornar = predioBase;

        mockMvc.perform(post("/api/predios/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(predioBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe actualizar un predio existente mediante PUT")
    void debeActualizarPredio() throws Exception {
        stubService.existePorIdResultado = true;
        stubService.predioARetornar = predioBase;

        mockMvc.perform(put("/api/predios/actualizar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(predioBase)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 404 en PUT si el predio no existe")
    void debeRetornar404AlActualizarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(put("/api/predios/actualizar/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(predioBase)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un predio con ID 99"));
    }

    @Test
    @DisplayName("Debe actualizar parcialmente un predio mediante PATCH")
    void debeActualizarParcial() throws Exception {
        stubService.predioARetornar = predioBase;
        PrediosDTO dto = new PrediosDTO("Nombre Editado", null, null, null);

        mockMvc.perform(patch("/api/predios/actualizarParcial/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 404 en PATCH si el servicio devuelve vacío")
    void debeRetornar404AlActualizarParcialInexistente() throws Exception {
        stubService.predioARetornar = null;
        PrediosDTO dto = new PrediosDTO("Test", null, null, null);

        mockMvc.perform(patch("/api/predios/actualizarParcial/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe eliminar un predio existente mediante DELETE")
    void debeEliminarPredio() throws Exception {
        stubService.existePorIdResultado = true;

        mockMvc.perform(delete("/api/predios/eliminar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Predio 1 eliminado"));
    }

    @Test
    @DisplayName("Debe retornar 404 en DELETE si el predio no existe")
    void debeRetornar404AlEliminarInexistente() throws Exception {
        stubService.existePorIdResultado = false;

        mockMvc.perform(delete("/api/predios/eliminar/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un predio con ID 99"));
    }
}