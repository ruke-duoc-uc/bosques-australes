package com.example.cliente.service;

import jakarta.persistence.EntityNotFoundException;
import com.example.cliente.dto.ClienteRequestDto;
import com.example.cliente.dto.ClienteResponseDto;
import com.example.cliente.exception.NegocioException;
import com.example.cliente.model.Cliente;
import com.example.cliente.model.TipoCliente;
import com.example.cliente.repository.ClienteRepository;
import com.example.cliente.service.ClienteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository; // Simula tu repositorio de clientes

    @InjectMocks
    private ClienteService clienteService; // Inyecta el repositorio simulado aquí

    private Cliente clienteBase;

    @BeforeEach
    void setUp() {
        // Inicializamos un cliente base para reutilizar en los tests
        clienteBase = new Cliente(
                "Forestal Valdivia S.A.", "77.345.678-9",
                "Sociedad Comercial Forestal Valdivia Limitada", "Esmeralda 450",
                "Valdivia", "Valdivia", "+56632221100",
                "finanzas@forvaldivia.cl", TipoCliente.EXPORTADOR, true
        );
        clienteBase.setId(1L);
    }

    @Nested
    @DisplayName("Pruebas para listarClientes")
    class ListarClientesTests {
        @Test
        @DisplayName("Debe retornar la lista completa de clientes")
        void debeRetornarListaDeClientes() {
            when(clienteRepository.findAll()).thenReturn(Arrays.asList(clienteBase));
            List<Cliente> resultado = clienteService.listarClientes();
            assertThat(resultado).isNotEmpty().hasSize(1);
            verify(clienteRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Pruebas para guardarCliente")
    class GuardarClienteTests {
        @Test
        @DisplayName("Debe guardar exitosamente cuando el RUT no existe")
        void debeGuardarClienteExitosamente() {
            when(clienteRepository.existsByRut(clienteBase.getRut())).thenReturn(false);
            when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteBase);

            Cliente guardado = clienteService.guardarCliente(clienteBase);

            assertThat(guardado).isNotNull();
            assertThat(guardado.getRut()).isEqualTo(clienteBase.getRut());
        }

        @Test
        @DisplayName("Debe lanzar NegocioException (409) cuando el RUT ya existe")
        void debeLanzarExcepcionCuandoRutExiste() {
            when(clienteRepository.existsByRut(clienteBase.getRut())).thenReturn(true);

            assertThatThrownBy(() -> clienteService.guardarCliente(clienteBase))
                    .isInstanceOf(NegocioException.class)
                    .hasMessageContaining("ya se encuentra registrado");

            verify(clienteRepository, never()).save(any(Cliente.class));
        }
    }

    @Nested
    @DisplayName("Pruebas para obtenerPorId")
    class ObtenerPorIdTests {
        @Test
        @DisplayName("Debe retornar el cliente cuando el ID existe")
        void debeRetornarClienteCuandoExiste() {
            when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteBase));
            Cliente encontrado = clienteService.obtenerPorId(1L);
            assertThat(encontrado).isNotNull();
            assertThat(encontrado.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Debe lanzar EntityNotFoundException cuando el ID no existe")
        void debeLanzarExcepcionCuandoIdNoExiste() {
            when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clienteService.obtenerPorId(99L))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("no existe en el sistema");
        }
    }

    @Nested
    @DisplayName("Pruebas para actualizarCliente")
    class ActualizarClienteTests {
        @Test
        @DisplayName("Debe actualizar los datos del cliente correctamente")
        void debeActualizarClienteExitosamente() {
            Cliente datosNuevos = new Cliente(
                    "Nuevo Nombre", "77.345.678-9", "Nueva Razon", "Nueva Direccion",
                    "Valdivia", "Valdivia", "+56632221100", "nuevo@email.cl",
                    TipoCliente.CONSTRUCTORA, true
            );

            when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteBase));
            when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteBase);

            Cliente actualizado = clienteService.actualizarCliente(1L, datosNuevos);

            assertThat(actualizado.getNombre()).isEqualTo("Nuevo Nombre");
            assertThat(actualizado.getEmail()).isEqualTo("nuevo@email.cl");
            verify(clienteRepository, times(1)).save(any(Cliente.class));
        }
    }

    @Nested
    @DisplayName("Pruebas para desactivarCliente")
    class DesactivarClienteTests {
        @Test
        @DisplayName("Debe cambiar el estado del cliente a inactivo (false)")
        void debeDesactivarClienteLogicamente() {
            when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteBase));
            when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteBase);

            clienteService.desactivarCliente(1L);

            assertThat(clienteBase.getEstado()).isFalse();
            verify(clienteRepository, times(1)).save(clienteBase);
        }
    }

    @Nested
    @DisplayName("Pruebas para buscarPorEstado y buscarPorNombre")
    class FiltrosClientesTests {
        @Test
        @DisplayName("Debe buscar clientes por estado")
        void debeBuscarPorEstado() {
            when(clienteRepository.findByEstado(true)).thenReturn(Arrays.asList(clienteBase));
            List<Cliente> resultado = clienteService.buscarPorEstado(true);
            assertThat(resultado).hasSize(1);
        }

        @Test
        @DisplayName("Debe buscar clientes por coincidencia de nombre")
        void debeBuscarPorNombre() {
            when(clienteRepository.findByNombreContainingIgnoreCase("Forestal")).thenReturn(Arrays.asList(clienteBase));
            List<Cliente> resultado = clienteService.buscarPorNombre("Forestal");
            assertThat(resultado).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Pruebas para obtenerDetalleCliente")
    class DetalleClienteTests {
        @Test
        @DisplayName("Debe estructurar el mapa de detalle correctamente")
        void debeRetornarMapaDeDetalle() {
            when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteBase));

            Map<String, Object> detalle = clienteService.obtenerDetalleCliente(1L);

            assertThat(detalle)
                    .containsEntry("id", 1L)
                    .containsEntry("nombre", "Forestal Valdivia S.A.")
                    .containsEntry("rut", "77.345.678-9");
        }
    }
}
