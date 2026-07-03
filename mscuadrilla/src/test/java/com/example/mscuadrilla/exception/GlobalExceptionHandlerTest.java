package com.example.mscuadrilla.exception;

import com.example.mscuadrilla.controller.CuadrillaController;
import com.example.mscuadrilla.model.Cuadrilla;
import com.example.mscuadrilla.service.CuadrillaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {
    private MockMvc mockMvc;
    private StubCuadrillaService stubService;

    private static class StubCuadrillaService extends CuadrillaService {
        public RuntimeException excepcionAAlzar;

        public StubCuadrillaService() {
            super(
                    org.mockito.Mockito.mock(com.example.mscuadrilla.repository.CuadrillaRepository.class),
                    crearBuilderFalso(),
                    "http://localhost:8086"
            );
        }

        private static org.springframework.web.client.RestClient.Builder crearBuilderFalso() {
            org.springframework.web.client.RestClient.Builder builder =
                    org.mockito.Mockito.mock(org.springframework.web.client.RestClient.Builder.class);
            org.springframework.web.client.RestClient restClient =
                    org.mockito.Mockito.mock(org.springframework.web.client.RestClient.class);
            org.mockito.Mockito.when(builder.baseUrl(org.mockito.Mockito.anyString())).thenReturn(builder);
            org.mockito.Mockito.when(builder.build()).thenReturn(restClient);
            return builder;
        }

        @Override
        public List<Cuadrilla> listarTodas() {
            if (excepcionAAlzar != null) throw excepcionAAlzar;
            return List.of();
        }

        @Override
        public Map<String, Object> obtenerDetalleCuadrilla(Long id) {
            if (excepcionAAlzar != null) throw excepcionAAlzar;
            return Map.of();
        }
    }

    @BeforeEach
    void setUp() {
        stubService = new StubCuadrillaService();
        CuadrillaController controller = new CuadrillaController(stubService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Debe capturar EntityNotFoundException y retornar estructura 404")
    void debeManejarEntityNotFoundException() throws Exception {
        stubService.excepcionAAlzar = new EntityNotFoundException("Cuadrilla no encontrada");

        mockMvc.perform(get("/api/v1/cuadrillas/{id}/detalle", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.mensaje").value("Cuadrilla no encontrada"));
    }

    @Test
    @DisplayName("Debe capturar NegocioException y retornar el status configurado")
    void debeManejarNegocioException() throws Exception {
        stubService.excepcionAAlzar = new NegocioException("Regla de negocio rota", HttpStatus.BAD_REQUEST.value());

        mockMvc.perform(get("/api/v1/cuadrillas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("NEGOCIO_ERROR"))
                .andExpect(jsonPath("$.mensaje").value("Regla de negocio rota"));
    }

    @Test
    @DisplayName("Debe capturar Exception general y retornar estructura 500")
    void debeManejarExcepcionesGenerales() throws Exception {
        stubService.excepcionAAlzar = new RuntimeException("Fallo catastrófico");

        mockMvc.perform(get("/api/v1/cuadrillas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.codigo").value("ERROR_INTERNO"))
                .andExpect(jsonPath("$.status").value(500));
    }
}