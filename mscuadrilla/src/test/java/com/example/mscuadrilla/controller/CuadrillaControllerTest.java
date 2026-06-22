package com.example.mscuadrilla.controller;

import com.example.mscuadrilla.model.Cuadrilla;
import com.example.mscuadrilla.service.CuadrillaService;
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

public class CuadrillaControllerTest {
    private MockMvc mockMvc;
    private StubCuadrillaService stubService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Cuadrilla cuadrillaBase;

    // --- STUB MEJORADO INMUNE A JAVA 26 Y AL CONSTRUCTOR CON RESTCLIENT ---
    private static class StubCuadrillaService extends CuadrillaService {
        public List<Cuadrilla> listaARetornar = new ArrayList<>();
        public Cuadrilla cuadrillaGuardada;
        public Cuadrilla cuadrillaActualizada;
        public Map<String, Object> detalleARetornar = new HashMap<>();

        public StubCuadrillaService() {
            // Pasamos mocks básicos directamente en el super para que el constructor de CuadrillaService no lance NullPointerException
            super(
                    org.mockito.Mockito.mock(com.example.mscuadrilla.repository.CuadrillaRepository.class),
                    crearBuilderFalso()
            );
        }

        // Método auxiliar para simular el encadenamiento .baseUrl().build() del RestClient
        private static org.springframework.web.client.RestClient.Builder crearBuilderFalso() {
            org.springframework.web.client.RestClient.Builder builder =
                    org.mockito.Mockito.mock(org.springframework.web.client.RestClient.Builder.class);
            org.springframework.web.client.RestClient restClient =
                    org.mockito.Mockito.mock(org.springframework.web.client.RestClient.class);

            org.mockito.Mockito.when(builder.baseUrl(org.mockito.ArgumentMatchers.anyString())).thenReturn(builder);
            org.mockito.Mockito.when(builder.build()).thenReturn(restClient);

            return builder;
        }

        @Override
        public List<Cuadrilla> listarTodas() {
            return listaARetornar;
        }

        @Override
        public Cuadrilla guardar(Cuadrilla cuadrilla) {
            return cuadrillaGuardada;
        }

        @Override
        public Cuadrilla actualizar(Long id, Cuadrilla datosNuevos) {
            return cuadrillaActualizada;
        }

        @Override
        public void eliminar(Long id) {
            // Simula éxito de borrado lógico o físico sin hacer nada
        }

        @Override
        public Map<String, Object> obtenerDetalleCuadrilla(Long id) {
            return detalleARetornar;
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubCuadrillaService();
        CuadrillaController controller = new CuadrillaController(stubService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Inicializamos los datos ficticios en formato Java Puro (sin Lombok)
        cuadrillaBase = new Cuadrilla();
        cuadrillaBase.setId(1L);
        cuadrillaBase.setNombre("Cuadrilla Alfa");
        cuadrillaBase.setZona("Zona Sur");

        // NOTA: Ajusta a setSpecialty(..) o setEspecialidad(..) según tu modelo real
        cuadrillaBase.setEspecialidad("Poda");

        cuadrillaBase.setEstado(true);
        cuadrillaBase.setTrabajadoresIds(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("Debe retornar 200 OK con la lista de cuadrillas")
    void debeRetornarListaCuadrillas() throws Exception {
        stubService.listaARetornar = Arrays.asList(cuadrillaBase);

        mockMvc.perform(get("/api/v1/cuadrillas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cuadrilla Alfa"));
    }

    @Test
    @DisplayName("Debe retornar 204 No Content si no hay cuadrillas")
    void debeRetornarNoContent() throws Exception {
        stubService.listaARetornar = new ArrayList<>();

        mockMvc.perform(get("/api/v1/cuadrillas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 201 Created al guardar una cuadrilla")
    void debeCrearCuadrilla() throws Exception {
        stubService.cuadrillaGuardada = cuadrillaBase;

        mockMvc.perform(post("/api/v1/cuadrillas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuadrillaBase)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 200 OK al actualizar una cuadrilla")
    void debeActualizarCuadrilla() throws Exception {
        stubService.cuadrillaActualizada = cuadrillaBase;

        mockMvc.perform(put("/api/v1/cuadrillas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuadrillaBase)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 204 No Content al eliminar físicamente una cuadrilla")
    void debeEliminarCuadrilla() throws Exception {
        mockMvc.perform(delete("/api/v1/cuadrillas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 OK con el detalle distribuido de la cuadrilla")
    void debeObtenerDetalleDistribuido() throws Exception {
        Map<String, Object> detalleFalso = new HashMap<>();
        detalleFalso.put("id", 1L);
        detalleFalso.put("nombre", "Cuadrilla Alfa");
        detalleFalso.put("trabajadores", Arrays.asList(Map.of("id", 99, "nombre", "Operario")));
        stubService.detalleARetornar = detalleFalso;

        mockMvc.perform(get("/api/v1/cuadrillas/{id}/detalle", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.trabajadores[0].id").value(99));
    }
}
