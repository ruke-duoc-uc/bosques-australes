package com.example.seguridad.service;

import com.example.seguridad.model.Accidente;
import com.example.seguridad.model.EstadoAccidente;
import com.example.seguridad.model.GravedadAccidente;
import com.example.seguridad.repository.SeguridadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
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

public class AccidenteServiceTest {

    private SeguridadRepository seguridadRepositoryMock;
    private RestTemplate restTemplateReal;
    private AccidenteService accidenteService;
    private Accidente accidenteBase;
    private boolean simularErrorHttp = false;

    @BeforeEach
    void setUp() {
        seguridadRepositoryMock = mock(SeguridadRepository.class);

        restTemplateReal = new RestTemplate();
        restTemplateReal.setInterceptors(List.of((request, body, execution) -> {
            if (simularErrorHttp) {
                throw new RuntimeException("404 Not Found");
            }
            String trabajadorJson = "{\"id\":10,\"nombre\":\"Trabajador Valido\"}";
            MockClientHttpResponse response = new MockClientHttpResponse(trabajadorJson.getBytes(), HttpStatus.OK);
            // AGREGAMOS EL CONTENIDO EN EL ENCABEZADO PARA QUE EL CONVERTIDOR DE SPRING ENTIENDA EL JSON
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        }));

        accidenteService = new AccidenteService(seguridadRepositoryMock, restTemplateReal);
        simularErrorHttp = false;

        accidenteBase = new Accidente();
        accidenteBase.setId(1L);
        accidenteBase.setTrabajadorId(10L);
        accidenteBase.setCuadrillaId(5L);
        accidenteBase.setDescripcion("Caída menor en terreno");
        accidenteBase.setGravedad(GravedadAccidente.LEVE);
    }

    @Test
    @DisplayName("Debe registrar un accidente LEVE asignando estado PENDIENTE")
    void debeRegistrarAccidenteLeve() {
        when(seguridadRepositoryMock.save(any(Accidente.class))).thenReturn(accidenteBase);

        Accidente resultado = accidenteService.registrar(accidenteBase);

        assertNotNull(resultado);
        assertEquals(EstadoAccidente.PENDIENTE, accidenteBase.getEstado());
        verify(seguridadRepositoryMock, times(1)).save(accidenteBase);
    }

    @Test
    @DisplayName("Debe registrar un accidente FATAL asignando estado INVESTIGANDO")
    void debeRegistrarAccidenteFatal() {
        accidenteBase.setGravedad(GravedadAccidente.FATAL);
        when(seguridadRepositoryMock.save(any(Accidente.class))).thenReturn(accidenteBase);

        Accidente resultado = accidenteService.registrar(accidenteBase);

        assertNotNull(resultado);
        assertEquals(EstadoAccidente.INVESTIGANDO, accidenteBase.getEstado());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el trabajador externo no existe en RRHH")
    void debeLanzarExcepcionTrabajadorNoExiste() {
        simularErrorHttp = true;

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            accidenteService.registrar(accidenteBase);
        });

        assertTrue(excepcion.getMessage().contains("No se puede registrar accidente"));
        verify(seguridadRepositoryMock, never()).save(any(Accidente.class));
    }

    @Test
    @DisplayName("Debe listar la totalidad de los accidentes registrados")
    void debeListarTodos() {
        when(seguridadRepositoryMock.findAll()).thenReturn(Arrays.asList(accidenteBase));

        List<Accidente> resultado = accidenteService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(seguridadRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener exitosamente un accidente por su ID")
    void debeObtenerPorId() {
        when(seguridadRepositoryMock.findById(1L)).thenReturn(Optional.of(accidenteBase));

        Accidente resultado = accidenteService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException si el accidente no existe")
    void debeLanzarExceptionAlNoEncontrarId() {
        when(seguridadRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            accidenteService.obtenerPorId(99L);
        });
    }
}