package com.example.seguridad.service;

import com.example.seguridad.model.Epp;
import com.example.seguridad.repository.EppRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EppServiceTest {
    private EppRepository eppRepositoryMock;
    private RestTemplate restTemplateReal;
    private EppService eppService;
    private Epp eppBase;
    private boolean simularErrorHttp = false;

    @BeforeEach
    void setUp() {
        eppRepositoryMock = mock(EppRepository.class);

        restTemplateReal = new RestTemplate();
        restTemplateReal.setInterceptors(List.of((request, body, execution) -> {
            if (simularErrorHttp) {
                throw new RuntimeException("Trabajador no encontrado");
            }
            String trabajadorJson = "{\"id\":10,\"nombre\":\"Trabajador Valido\"}";
            MockClientHttpResponse response = new MockClientHttpResponse(trabajadorJson.getBytes(), HttpStatus.OK);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        }));

        eppService = new EppService(eppRepositoryMock, restTemplateReal);
        simularErrorHttp = false;

        eppBase = new Epp();
        eppBase.setId(1L);
        eppBase.setTrabajadorId(10L);
        eppBase.setTipo("Casco de seguridad");
        eppBase.setActivo(true);
        eppBase.setObservaciones("Entrega inicial");
    }

    @Test
    @DisplayName("Debe registrar un EPP exitosamente transformando el tipo a mayúsculas")
    void debeRegistrarEppExitosamente() {
        when(eppRepositoryMock.save(any(Epp.class))).thenReturn(eppBase);

        Epp resultado = eppService.registrar(eppBase);

        assertNotNull(resultado);
        assertEquals("CASCO DE SEGURIDAD", eppBase.getTipo());
        assertTrue(eppBase.isActivo());
        verify(eppRepositoryMock, times(1)).save(eppBase);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el trabajador no existe en el sistema externo al registrar EPP")
    void debeLanzarExcepcionTrabajadorNoExiste() {
        simularErrorHttp = true;

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            eppService.registrar(eppBase);
        });

        assertTrue(excepcion.getMessage().contains("No se puede registrar el EPP"));
        verify(eppRepositoryMock, never()).save(any(Epp.class));
    }

    @Test
    @DisplayName("Debe actualizar los datos de un EPP existente")
    void debeActualizarEpp() {
        Epp eppModificado = new Epp();
        eppModificado.setTipo("Gafas de protección");
        eppModificado.setObservaciones("Cambio por desgaste");

        when(eppRepositoryMock.findById(1L)).thenReturn(Optional.of(eppBase));
        when(eppRepositoryMock.save(any(Epp.class))).thenReturn(eppBase);

        Epp resultado = eppService.actualizar(1L, eppModificado);

        assertNotNull(resultado);
        assertEquals("GAFAS DE PROTECCIÓN", eppBase.getTipo());
        assertEquals("Cambio por desgaste", eppBase.getObservaciones());
    }

    @Test
    @DisplayName("Debe realizar desactivación lógica de un EPP")
    void debeDesactivarEpp() {
        when(eppRepositoryMock.findById(1L)).thenReturn(Optional.of(eppBase));
        when(eppRepositoryMock.save(any(Epp.class))).thenReturn(eppBase);

        assertDoesNotThrow(() -> eppService.desactivar(1L));

        assertFalse(eppBase.isActivo());
        verify(eppRepositoryMock, times(1)).save(eppBase);
    }

    @Test
    @DisplayName("Debe retornar true si el trabajador posee al menos un EPP activo")
    void debeRetornarTrueSiTieneEppActivo() {
        when(eppRepositoryMock.findByTrabajadorIdAndActivoTrue(10L)).thenReturn(Arrays.asList(eppBase));

        boolean tieneActivo = eppService.trabajadorTieneEppActivo(10L);

        assertTrue(tieneActivo);
    }

    @Test
    @DisplayName("Debe retornar false si el trabajador no cuenta con EPPs activos")
    void debeRetornarFalseSiNoTieneEppActivo() {
        when(eppRepositoryMock.findByTrabajadorIdAndActivoTrue(20L)).thenReturn(List.of());

        boolean tieneActivo = eppService.trabajadorTieneEppActivo(20L);

        assertFalse(tieneActivo);
    }

    @Test
    @DisplayName("Debe listar todos los registros de EPP")
    void debeListarTodos() {
        when(eppRepositoryMock.findAll()).thenReturn(Arrays.asList(eppBase));

        List<Epp> resultado = eppService.listarTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException si se busca un ID inexistente")
    void debeLanzarExcepcionAlNoEncontrarId() {
        when(eppRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            eppService.obtenerPorId(99L);
        });
    }
}