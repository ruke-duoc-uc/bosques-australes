package com.example.seguridad.controller;

import com.example.seguridad.dto.EppRequestDto;
import com.example.seguridad.model.Epp;
import com.example.seguridad.service.EppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EppControllerTest {
    private MockMvc mockMvc;
    private StubEppService stubService;
    private ObjectMapper objectMapper;
    private Epp eppBase;

    private static class StubEppService extends EppService {
        public List<Epp> listaAFiltrar = List.of();
        public Epp eppARetornar;
        public boolean flagTieneActivo = false;

        public StubEppService() {
            super(
                    Mockito.mock(com.example.seguridad.repository.EppRepository.class),
                    crearRestTemplateSimulado()
            );
        }

        private static RestTemplate crearRestTemplateSimulado() {
            RestTemplate rt = new RestTemplate();
            rt.setInterceptors(List.of((req, body, exec) ->
                    new MockClientHttpResponse("{\"id\":10}".getBytes(), HttpStatus.OK)));
            return rt;
        }

        @Override
        public List<Epp> listarTodos() {
            return listaAFiltrar;
        }

        @Override
        public Epp obtenerPorId(Long id) {
            return eppARetornar;
        }

        @Override
        public List<Epp> listarPorTrabajador(Long trabajadorId) {
            return listaAFiltrar;
        }

        @Override
        public Epp registrar(Epp epp) {
            return eppARetornar != null ? eppARetornar : epp;
        }

        @Override
        public Epp actualizar(Long id, Epp eppActualizado) {
            return eppARetornar != null ? eppARetornar : eppActualizado;
        }

        @Override
        public void desactivar(Long id) {
            // No hace nada
        }

        @Override
        public boolean trabajadorTieneEppActivo(Long trabajadorId) {
            return flagTieneActivo;
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubEppService();
        EppController controller = new EppController(stubService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();

        eppBase = new Epp();
        eppBase.setId(1L);
        eppBase.setTrabajadorId(10L);
        eppBase.setTipo("CASCO");
        eppBase.setActivo(true);
        eppBase.setObservaciones("Equipo nuevo");
    }

    @Test
    @DisplayName("Debe listar todos los EPPs registrados mediante GET")
    void debeListarTodos() throws Exception {
        stubService.listaAFiltrar = Arrays.asList(eppBase);

        mockMvc.perform(get("/api/v1/epps")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tipo").value("CASCO"));
    }

    @Test
    @DisplayName("Debe obtener un EPP por su ID mediante GET")
    void debeObtenerPorId() throws Exception {
        stubService.eppARetornar = eppBase;

        mockMvc.perform(get("/api/v1/epps/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipo").value("CASCO"));
    }

    @Test
    @DisplayName("Debe listar EPPs asociados a un trabajador específico")
    void debeListarPorTrabajador() throws Exception {
        stubService.listaAFiltrar = Arrays.asList(eppBase);

        mockMvc.perform(get("/api/v1/epps/trabajador/{trabajadorId}", 10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("Debe verificar vigencia positiva del EPP para un trabajador")
    void debeVerificarVigenciaPositiva() throws Exception {
        stubService.flagTieneActivo = true;

        mockMvc.perform(get("/api/v1/epps/trabajador/{id}/vigente", 10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eppVigente").value(true))
                .andExpect(jsonPath("$.mensaje").value("El trabajador cuenta con EPP activos registrados."));
    }

    @Test
    @DisplayName("Debe alertar si un trabajador carece de EPPs vigentes")
    void debeVerificarVigenciaNegativa() throws Exception {
        stubService.flagTieneActivo = false;

        mockMvc.perform(get("/api/v1/epps/trabajador/{id}/vigente", 20L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eppVigente").value(false))
                .andExpect(jsonPath("$.mensaje").value("ALERTA: El trabajador no tiene EPP activos o registrados."));
    }

    @Test
    @DisplayName("Debe registrar un nuevo EPP mediante un POST exitoso")
    void debeRegistrarEpp() throws Exception {
        EppRequestDto dto = new EppRequestDto();
        dto.setTrabajadorId(10L);
        dto.setTipo("Casco");
        dto.setFechaEntrega("2026-06-22");
        dto.setFechaVencimiento("2027-06-22");

        stubService.eppARetornar = eppBase;

        mockMvc.perform(post("/api/v1/epps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Debe actualizar un EPP a través de PUT")
    void debeActualizarEpp() throws Exception {
        stubService.eppARetornar = eppBase;

        EppRequestDto dto = new EppRequestDto();
        dto.setTrabajadorId(10L); // El ID es mandatorio en casi todo request para amarrar la entidad
        dto.setTipo("CASCO MODIFICADO");
        dto.setFechaEntrega("2026-06-22");
        dto.setFechaVencimiento("2027-06-22");

        mockMvc.perform(put("/api/v1/epps/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Debe desactivar un EPP lógicamente mediante DELETE devolviendo 204")
    void debeDesactivarEpp() throws Exception {
        mockMvc.perform(delete("/api/v1/epps/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
