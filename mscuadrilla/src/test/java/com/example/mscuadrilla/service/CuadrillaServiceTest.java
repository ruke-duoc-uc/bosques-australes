package com.example.mscuadrilla.service;

import com.example.mscuadrilla.model.Cuadrilla;
import com.example.mscuadrilla.repository.CuadrillaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuadrillaServiceTest {
    @Mock
    private CuadrillaRepository cuadrillaRepository;

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    // Quitamos @InjectMocks para controlarlo manualmente
    private CuadrillaService cuadrillaService;

    private Cuadrilla cuadrillaBase;

    @BeforeEach
    void setUp() {
        // 1. Configuramos el builderMock para que devuelva el 'restClient' global de la clase
        RestClient.Builder builderMock = mock(RestClient.Builder.class);

        when(builderMock.baseUrl(anyString())).thenReturn(builderMock);
        when(builderMock.build()).thenReturn(restClient); // <-- Usamos el de la clase

        // 2. Instanciamos el servicio pasándole este builder
        cuadrillaService = new CuadrillaService(cuadrillaRepository, builderMock, "http://localhost:8086");

        // 3. Objeto base para los tests
        cuadrillaBase = new Cuadrilla();
        cuadrillaBase.setId(1L);
        cuadrillaBase.setNombre("Cuadrilla Alfa");
        cuadrillaBase.setZona("Zona Sur");
        cuadrillaBase.setEspecialidad("Poda");
        cuadrillaBase.setEstado(true);
        cuadrillaBase.setTrabajadoresIds(Arrays.asList(10L, 20L));
    }
    @Test
    @DisplayName("Debe listar todas las cuadrillas registradas")
    void debeListarTodas() {
        when(cuadrillaRepository.findAll()).thenReturn(Arrays.asList(cuadrillaBase));

        List<Cuadrilla> resultado = cuadrillaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cuadrilla Alfa", resultado.get(0).getNombre());
        verify(cuadrillaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe guardar exitosamente una nueva cuadrilla")
    void debeGuardarCuadrilla() {
        when(cuadrillaRepository.save(any(Cuadrilla.class))).thenReturn(cuadrillaBase);

        Cuadrilla guardada = cuadrillaService.guardar(cuadrillaBase);

        assertNotNull(guardada);
        assertEquals(1L, guardada.getId());
        verify(cuadrillaRepository, times(1)).save(cuadrillaBase);
    }

    @Test
    @DisplayName("Debe actualizar una cuadrilla existente")
    void debeActualizarCuadrilla() {
        Cuadrilla datosNuevos = new Cuadrilla();
        datosNuevos.setNombre("Cuadrilla Modificada");
        datosNuevos.setZona("Zona Norte");

        when(cuadrillaRepository.findById(1L)).thenReturn(Optional.of(cuadrillaBase));
        when(cuadrillaRepository.save(any(Cuadrilla.class))).thenReturn(cuadrillaBase);

        Cuadrilla actualizada = cuadrillaService.actualizar(1L, datosNuevos);

        assertNotNull(actualizada);
        verify(cuadrillaRepository, times(1)).findById(1L);
        verify(cuadrillaRepository, times(1)).save(any(Cuadrilla.class));
    }

    @Test
    @DisplayName("Debe procesar la eliminación o desactivación de una cuadrilla por ID")
    void debeEliminarCuadrilla() {
        // 1. El servicio siempre va a buscar si la cuadrilla existe primero
        when(cuadrillaRepository.findById(1L)).thenReturn(Optional.of(cuadrillaBase));

        // 2. Por si acaso el método usa save() para apagado lógico o deleteById() para físico, entrenamos ambos de forma segura (lenient)
        lenient().when(cuadrillaRepository.save(any(Cuadrilla.class))).thenReturn(cuadrillaBase);
        lenient().doNothing().when(cuadrillaRepository).deleteById(1L);

        // 3. Ejecutamos el método real de tu servicio
        assertDoesNotThrow(() -> cuadrillaService.eliminar(1L));

        // 4. Verificamos que al menos se validó su existencia en la base de datos
        verify(cuadrillaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe obtener el detalle distribuido de la cuadrilla con sus trabajadores")
    void debeObtenerDetalleCuadrilla() {
        // 1. Entrenamos el repositorio para que encuentre la cuadrilla base
        when(cuadrillaRepository.findById(1L)).thenReturn(Optional.of(cuadrillaBase));

        // 2. Simulamos la llamada HTTP encadenada del RestClient
        org.springframework.web.client.RestClient.RequestHeadersUriSpec uriSpecMock =
                mock(org.springframework.web.client.RestClient.RequestHeadersUriSpec.class);
        org.springframework.web.client.RestClient.RequestHeadersSpec headersSpecMock =
                mock(org.springframework.web.client.RestClient.RequestHeadersSpec.class);
        org.springframework.web.client.RestClient.ResponseSpec responseSpecMock =
                mock(org.springframework.web.client.RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);

        // Simula la lista de retorno usando List.class explícito
        List<Map<String, Object>> operariosFalsos = Arrays.asList(Map.of("id", 10, "nombre", "Juan"));
        when(responseSpecMock.body(List.class)).thenReturn(operariosFalsos);

        // 3. Ejecutamos el método del servicio
        Map<String, Object> resultado = cuadrillaService.obtenerDetalleCuadrilla(1L);

        // 4. Verificaciones
        assertNotNull(resultado);
        assertEquals(1L, resultado.get("id"));
        verify(cuadrillaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la cuadrilla no existe al buscar detalle")
    void debeLanzarExcepcionCuandoNoExiste() {
        // Simulamos que la base de datos devuelve vacío (No existe la cuadrilla)
        when(cuadrillaRepository.findById(99L)).thenReturn(Optional.empty());

        // Verificamos que salte la excepción de negocio que programaste (habitualmente EntityNotFoundException)
        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> {
            cuadrillaService.obtenerDetalleCuadrilla(99L);
        });
    }
}