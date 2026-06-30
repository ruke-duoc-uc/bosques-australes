package com.example.msplanCosecha.service;

import com.example.msplanCosecha.client.EspeciesClient;
import com.example.msplanCosecha.client.EspeciesDTO;
import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.model.PlanCosechaDTO;
import com.example.msplanCosecha.repository.PlanCosechaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanCosechaServiceTest {

    @Mock
    private PlanCosechaRepository planCosechaRepository;

    @Mock
    private EspeciesClient especiesClient;

    @InjectMocks
    private PlanCosechaService planCosechaService;

    @Test
    void testListarPlanCosecha() {
        PlanCosecha plan = new PlanCosecha();
        when(planCosechaRepository.findAll()).thenReturn(List.of(plan));

        List<PlanCosecha> resultado = planCosechaService.listarPlanCosecha();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(planCosechaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_CuandoExiste() {
        PlanCosecha plan = new PlanCosecha();
        when(planCosechaRepository.findById(1L)).thenReturn(Optional.of(plan));

        PlanCosecha resultado = planCosechaService.obtenerPorId(1L);

        assertNotNull(resultado);
        verify(planCosechaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPorId_CuandoNoExiste() {
        when(planCosechaRepository.findById(1L)).thenReturn(Optional.empty());

        PlanCosecha resultado = planCosechaService.obtenerPorId(1L);

        assertNull(resultado);
        verify(planCosechaRepository, times(1)).findById(1L);
    }

    @Test
    void testExistePorid() {
        when(planCosechaRepository.existsById(1L)).thenReturn(true);

        Boolean existe = planCosechaService.existePorid(1L);

        assertTrue(existe);
        verify(planCosechaRepository, times(1)).existsById(1L);
    }

    @Test
    void testGuardarPlanCosecha() {
        PlanCosecha entrada = new PlanCosecha();
        entrada.setAlturaPromedio(25.5);
        entrada.setEdadRodal(15L);
        entrada.setDescripcion("Plan Base");

        EspeciesDTO dto = new EspeciesDTO(2L, "Pino");

        when(especiesClient.obtenerDatosCliente(2L)).thenReturn(dto);
        when(planCosechaRepository.save(any(PlanCosecha.class))).thenAnswer(i -> i.getArgument(0));

        PlanCosecha resultado = planCosechaService.guardarPlanCosecha(2L, entrada);

        assertNotNull(resultado);
        assertEquals("Pino", resultado.getEspecie());
        assertEquals(25.5, resultado.getAlturaPromedio());
        assertEquals(15L, resultado.getEdadRodal());
        assertEquals("Plan Base", resultado.getDescripcion());
        verify(especiesClient, times(1)).obtenerDatosCliente(2L);
        verify(planCosechaRepository, times(1)).save(any(PlanCosecha.class));
    }

    @Test
    void testActualizarPlanCompleto_CuandoExiste() {
        PlanCosecha existente = new PlanCosecha();
        PlanCosecha nuevosDatos = new PlanCosecha();
        nuevosDatos.setAlturaPromedio(30.0);
        nuevosDatos.setEdadRodal(20L);
        nuevosDatos.setDescripcion("Plan Renovado");

        EspeciesDTO dto = new EspeciesDTO(2L, "Eucalipto");

        when(especiesClient.obtenerDatosCliente(2L)).thenReturn(dto);
        when(planCosechaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(planCosechaRepository.save(any(PlanCosecha.class))).thenAnswer(i -> i.getArgument(0));

        Optional<PlanCosecha> resultado = planCosechaService.actualizarPlanCompleto(1L, 2L, nuevosDatos);

        assertTrue(resultado.isPresent());
        assertEquals("Eucalipto", resultado.get().getEspecie());
        assertEquals(30.0, resultado.get().getAlturaPromedio());
        assertEquals(20L, resultado.get().getEdadRodal());
        verify(planCosechaRepository, times(1)).findById(1L);
        verify(planCosechaRepository, times(1)).save(any(PlanCosecha.class));
    }

    @Test
    void testActualizarPlanCompleto_CuandoNoExiste() {
        PlanCosecha nuevosDatos = new PlanCosecha();
        EspeciesDTO dto = new EspeciesDTO(2L, "Eucalipto");

        when(especiesClient.obtenerDatosCliente(2L)).thenReturn(dto);
        when(planCosechaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<PlanCosecha> resultado = planCosechaService.actualizarPlanCompleto(1L, 2L, nuevosDatos);

        assertTrue(resultado.isEmpty());
        verify(planCosechaRepository, times(1)).findById(1L);
        verify(planCosechaRepository, never()).save(any(PlanCosecha.class));
    }

    @Test
    void testActualizarPlanCosecha_ConTodosLosCampos() {
        PlanCosecha existente = new PlanCosecha();
        PlanCosechaDTO dtoCampos = new PlanCosechaDTO(3L, 35.2, 18L, "Parcial");
        EspeciesDTO especieDto = new EspeciesDTO(3L, "Roble");

        when(planCosechaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(especiesClient.obtenerDatosCliente(3L)).thenReturn(especieDto);
        when(planCosechaRepository.save(any(PlanCosecha.class))).thenAnswer(i -> i.getArgument(0));

        Optional<PlanCosecha> resultado = planCosechaService.actualizarPlanCosecha(1L, dtoCampos);

        assertTrue(resultado.isPresent());
        assertEquals("Roble", resultado.get().getEspecie());
        assertEquals(35.2, resultado.get().getAlturaPromedio());
        assertEquals(18L, resultado.get().getEdadRodal());
        verify(planCosechaRepository, times(1)).save(any(PlanCosecha.class));
    }

    @Test
    void testActualizarPlanCosecha_ConCamposNulos() {
        PlanCosecha existente = new PlanCosecha();
        existente.setDescripcion("Original");
        PlanCosechaDTO dtoNulo = new PlanCosechaDTO(null, null, null, null);

        when(planCosechaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(planCosechaRepository.save(any(PlanCosecha.class))).thenAnswer(i -> i.getArgument(0));

        Optional<PlanCosecha> resultado = planCosechaService.actualizarPlanCosecha(1L, dtoNulo);

        assertTrue(resultado.isPresent());
        assertEquals("Original", resultado.get().getDescripcion());
        verify(especiesClient, never()).obtenerDatosCliente(anyLong());
        verify(planCosechaRepository, times(1)).save(any(PlanCosecha.class));
    }

    @Test
    void testActualizarPlanCosecha_CuandoNoExiste() {
        PlanCosechaDTO dto = new PlanCosechaDTO(1L, 20.0, 10L, "Test");
        when(planCosechaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<PlanCosecha> resultado = planCosechaService.actualizarPlanCosecha(1L, dto);

        assertTrue(resultado.isEmpty());
        verify(planCosechaRepository, times(1)).findById(1L);
        verify(planCosechaRepository, never()).save(any(PlanCosecha.class));
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(planCosechaRepository).deleteById(1L);

        planCosechaService.eliminarPorId(1L);

        verify(planCosechaRepository, times(1)).deleteById(1L);
    }
}