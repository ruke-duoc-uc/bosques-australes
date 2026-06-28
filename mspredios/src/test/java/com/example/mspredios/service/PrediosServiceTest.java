package com.example.mspredios.service;

import com.example.mspredios.model.Predios;
import com.example.mspredios.model.PrediosDTO;
import com.example.mspredios.repository.PrediosRepository;
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
class PrediosServiceTest {

    @Mock
    private PrediosRepository prediosRepository;

    @InjectMocks
    private PrediosService prediosService;

    @Test
    void testListarPredios() {
        Predios predio = new Predios();
        when(prediosRepository.findAll()).thenReturn(List.of(predio));

        List<Predios> resultado = prediosService.listarPredios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(prediosRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_CuandoExiste() {
        Predios predio = new Predios();
        when(prediosRepository.findById(1L)).thenReturn(Optional.of(predio));

        Predios resultado = prediosService.buscarPorId(1L);

        assertNotNull(resultado);
        verify(prediosRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_CuandoNoExiste() {
        when(prediosRepository.findById(1L)).thenReturn(Optional.empty());

        Predios resultado = prediosService.buscarPorId(1L);

        assertNull(resultado);
        verify(prediosRepository, times(1)).findById(1L);
    }

    @Test
    void testExistePorId() {
        when(prediosRepository.existsById(1L)).thenReturn(true);

        Boolean existe = prediosService.existePorId(1L);

        assertTrue(existe);
        verify(prediosRepository, times(1)).existsById(1L);
    }

    @Test
    void testGuardarPredio() {
        Predios predio = new Predios();
        when(prediosRepository.save(predio)).thenReturn(predio);

        Predios resultado = prediosService.guardarPredio(predio);

        assertNotNull(resultado);
        verify(prediosRepository, times(1)).save(predio);
    }

    @Test
    void testActualizarPredio_CuandoExiste() {
        Predios predioExistente = new Predios();
        Predios nuevosDatos = new Predios();
        nuevosDatos.setNombre("Nuevo Nombre");
        nuevosDatos.setCiudad("Nueva Ciudad");
        nuevosDatos.setComuna("Nueva Comuna");
        nuevosDatos.setDireccion("Nueva Direccion");

        when(prediosRepository.findById(1L)).thenReturn(Optional.of(predioExistente));
        when(prediosRepository.save(any(Predios.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Predios> resultado = prediosService.actualizarPredio(1L, nuevosDatos);

        assertTrue(resultado.isPresent());
        assertEquals("Nuevo Nombre", resultado.get().getNombre());
        verify(prediosRepository, times(1)).findById(1L);
        verify(prediosRepository, times(1)).save(any(Predios.class));
    }

    @Test
    void testActualizarParcialPredios_ConTodosLosCampos() {
        Predios predioExistente = new Predios();
        PrediosDTO dto = new PrediosDTO("Nombre", "Ciudad", "Comuna", "Direccion");

        when(prediosRepository.findById(1L)).thenReturn(Optional.of(predioExistente));
        when(prediosRepository.save(any(Predios.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<?> resultado = prediosService.actualizarParcialPredios(1L, dto);

        assertTrue(resultado.isPresent());
        verify(prediosRepository, times(1)).save(any(Predios.class));
    }

    @Test
    void testActualizarParcialPredios_ConCamposNulos() {
        // Este test es vital para JaCoCo porque evalúa el escenario donde los "if" no se cumplen
        Predios predioExistente = new Predios();
        predioExistente.setNombre("Original");
        PrediosDTO dtoVacio = new PrediosDTO(null, null, null, null);

        when(prediosRepository.findById(1L)).thenReturn(Optional.of(predioExistente));
        when(prediosRepository.save(any(Predios.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<?> resultado = prediosService.actualizarParcialPredios(1L, dtoVacio);

        assertTrue(resultado.isPresent());
        verify(prediosRepository, times(1)).save(any(Predios.class));
    }

    @Test
    void testEliminarPredio() {
        doNothing().when(prediosRepository).deleteById(1L);

        prediosService.eliminarPredio(1L);

        verify(prediosRepository, times(1)).deleteById(1L);
    }
}