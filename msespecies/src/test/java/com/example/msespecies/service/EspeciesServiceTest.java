package com.example.msespecies.service;

import com.example.msespecies.model.Especies;
import com.example.msespecies.model.EspeciesDTO;
import com.example.msespecies.repository.EspeciesRepository;
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
class EspeciesServiceTest {

    @Mock
    private EspeciesRepository especiesRepository;

    @InjectMocks
    private EspeciesService especiesService;

    @Test
    void testListarEspecies() {
        Especies especie = new Especies();
        when(especiesRepository.findAll()).thenReturn(List.of(especie));

        List<Especies> resultado = especiesService.listarEspecies();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(especiesRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_CuandoExiste() {
        Especies especie = new Especies();
        when(especiesRepository.findById(1L)).thenReturn(Optional.of(especie));

        Especies resultado = especiesService.buscarPorId(1L);

        assertNotNull(resultado);
        verify(especiesRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_CuandoNoExiste() {
        when(especiesRepository.findById(1L)).thenReturn(Optional.empty());

        Especies resultado = especiesService.buscarPorId(1L);

        assertNull(resultado);
        verify(especiesRepository, times(1)).findById(1L);
    }

    @Test
    void testExistePorId() {
        when(especiesRepository.existsById(1L)).thenReturn(true);

        Boolean existe = especiesService.existePorId(1L);

        assertTrue(existe);
        verify(especiesRepository, times(1)).existsById(1L);
    }

    @Test
    void testGuardarEspecie() {
        Especies especie = new Especies();
        when(especiesRepository.save(especie)).thenReturn(especie);

        Especies resultado = especiesService.guardarEspecie(especie);

        assertNotNull(resultado);
        verify(especiesRepository, times(1)).save(especie);
    }

    @Test
    void testActualizarEspecie_CuandoExiste() {
        Especies especieExistente = new Especies();
        Especies nuevosDatos = new Especies();
        nuevosDatos.setNombre("Pino");
        nuevosDatos.setUso("Construcción");
        nuevosDatos.setCalidad("Alta");
        nuevosDatos.setColor("Claro");

        when(especiesRepository.findById(1L)).thenReturn(Optional.of(especieExistente));
        when(especiesRepository.save(any(Especies.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Especies> resultado = especiesService.actualizarEspecie(1L, nuevosDatos);

        assertTrue(resultado.isPresent());
        assertEquals("Pino", resultado.get().getNombre());
        verify(especiesRepository, times(1)).findById(1L);
        verify(especiesRepository, times(1)).save(any(Especies.class));
    }

    @Test
    void testActualizarEspecie_CuandoNoExiste() {
        Especies nuevosDatos = new Especies();
        when(especiesRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Especies> resultado = especiesService.actualizarEspecie(1L, nuevosDatos);

        assertTrue(resultado.isEmpty());
        verify(especiesRepository, times(1)).findById(1L);
        verify(especiesRepository, never()).save(any(Especies.class));
    }

    @Test
    void testActualizarParcialEspecie_ConTodosLosCampos() {
        Especies especieExistente = new Especies();
        EspeciesDTO dto = new EspeciesDTO("Eucalipto", "Celulosa", "Media", "Blanco");

        when(especiesRepository.findById(1L)).thenReturn(Optional.of(especieExistente));
        when(especiesRepository.save(any(Especies.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<?> resultado = especiesService.actualizarParcialEspecie(1L, dto);

        assertTrue(resultado.isPresent());
        verify(especiesRepository, times(1)).findById(1L);
        verify(especiesRepository, times(1)).save(any(Especies.class));
    }

    @Test
    void testActualizarParcialEspecie_ConCamposNulos() {
        Especies especieExistente = new Especies();
        especieExistente.setNombre("Original");
        EspeciesDTO dtoVacio = new EspeciesDTO(null, null, null, null);

        when(especiesRepository.findById(1L)).thenReturn(Optional.of(especieExistente));
        when(especiesRepository.save(any(Especies.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<?> resultado = especiesService.actualizarParcialEspecie(1L, dtoVacio);

        assertTrue(resultado.isPresent());
        verify(especiesRepository, times(1)).findById(1L);
        verify(especiesRepository, times(1)).save(any(Especies.class));
    }

    @Test
    void testActualizarParcialEspecie_CuandoNoExiste() {
        EspeciesDTO dto = new EspeciesDTO("Test", "Test", "Test", "Test");
        when(especiesRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<?> resultado = especiesService.actualizarParcialEspecie(1L, dto);

        assertTrue(resultado.isEmpty());
        verify(especiesRepository, times(1)).findById(1L);
        verify(especiesRepository, never()).save(any(Especies.class));
    }

    @Test
    void testEliminarEspecie() {
        doNothing().when(especiesRepository).deleteById(1L);

        especiesService.eliminarEspecie(1L);

        verify(especiesRepository, times(1)).deleteById(1L);
    }
}