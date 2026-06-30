package com.example.msfactura.service;

import com.example.msfactura.client.ClientesClient;
import com.example.msfactura.client.ClientesDTO;
import com.example.msfactura.client.PrediosClient;
import com.example.msfactura.client.PrediosDTO;
import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
import com.example.msfactura.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FacturaServiceTest {

    private FacturaRepository facturaRepositoryMock;
    private PrediosClient prediosClientMock;
    private ClientesClient clientesClientMock;

    private FacturaService facturaService;
    private Factura facturaBase;

    @BeforeEach
    void setUp() {
        facturaRepositoryMock = mock(FacturaRepository.class);
        prediosClientMock = mock(PrediosClient.class);
        clientesClientMock = mock(ClientesClient.class);

        facturaService = new FacturaService(facturaRepositoryMock, prediosClientMock, clientesClientMock);

        facturaBase = new Factura();
        facturaBase.setId(1L);
        facturaBase.setNumFactura(101L);
        facturaBase.setGiro("Agricola");
        facturaBase.setMonto(250000.0);
    }

    @Test
    @DisplayName("Debe listar todas las facturas de la base de datos")
    void testListarFactura() {
        when(facturaRepositoryMock.findAll()).thenReturn(Arrays.asList(facturaBase));

        List<Factura> resultado = facturaService.listarFactura();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(facturaRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar y retornar una factura por su ID")
    void testBuscarPorId() {
        when(facturaRepositoryMock.findById(1L)).thenReturn(Optional.of(facturaBase));

        Factura resultado = facturaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(101L, resultado.getNumFactura());
        verify(facturaRepositoryMock, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar verdadero si la factura existe por ID")
    void testExistePorId() {
        when(facturaRepositoryMock.existsById(1L)).thenReturn(true);

        Boolean existe = facturaService.existePorId(1L);

        assertTrue(existe);
        verify(facturaRepositoryMock, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Debe mapear datos externos y guardar la factura correctamente")
    void testGuardarFactura() {
        PrediosDTO predioMock = new PrediosDTO(10L, "Fundo Central", "Talca", "Maule");
        ClientesDTO clienteMock = new ClientesDTO("AgroVentas S.A.", "Curico", "Curico", "912345678");

        when(prediosClientMock.obtenerDatosPredio(10L)).thenReturn(predioMock);
        when(clientesClientMock.obtenerDatosCliente(5L)).thenReturn(clienteMock);
        when(facturaRepositoryMock.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Factura guardada = facturaService.guardarFactura(10L, 5L, facturaBase);

        assertNotNull(guardada);
        assertEquals("TalcaMaule", guardada.getDireccion());
        assertEquals("AgroVentas S.A.", guardada.getRazonSocial());
        verify(facturaRepositoryMock, times(1)).save(any(Factura.class));
    }

    @Test
    @DisplayName("Debe actualizar por completo una factura consumiendo apis externas")
    void testActualizarFacturaCompleta() {
        Factura existente = new Factura();
        existente.setId(1L);

        PrediosDTO predioMock = new PrediosDTO(10L, "Fundo Las Camelias", "Valdivia", "Mariquina");
        ClientesDTO clienteMock = new ClientesDTO("Forestal Sur", "Concepcion", "Concepcion", "987654321");

        when(facturaRepositoryMock.findById(1L)).thenReturn(Optional.of(existente));
        when(prediosClientMock.obtenerDatosPredio(10L)).thenReturn(predioMock);
        when(clientesClientMock.obtenerDatosCliente(5L)).thenReturn(clienteMock);
        when(facturaRepositoryMock.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Factura> optResultado = facturaService.actualizarFacturaCompleta(1L, 10L, 5L, facturaBase);

        assertTrue(optResultado.isPresent());
        Factura resultado = optResultado.get();
        assertEquals("ValdiviaMariquina", resultado.getDireccion());
        assertEquals("Forestal Sur", resultado.getRazonSocial());
    }

    @Test
    @DisplayName("Debe retornar Optional.empty si la factura no existe al intentar actualizar completa")
    void testActualizarFacturaCompleta_NoExiste() {
        when(facturaRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        Optional<Factura> optResultado = facturaService.actualizarFacturaCompleta(99L, 10L, 5L, facturaBase);

        assertTrue(optResultado.isEmpty());
        verify(facturaRepositoryMock, never()).save(any(Factura.class));
    }

    @Test
    @DisplayName("Debe actualizar parcialmente solo los campos enviados en el FacturaDTO")
    void testActualizarFacturaParcial_ConCambiosMixtos() {
        Factura existente = new Factura();
        existente.setNumFactura(50L);
        existente.setGiro("Ganaderia");

        FacturaDTO dto = new FacturaDTO(12L, null, null, null, 350000.0);
        PrediosDTO predioMock = new PrediosDTO(12L, "Fundo El Toro", "Osorno", "Negro");

        when(facturaRepositoryMock.findById(1L)).thenReturn(Optional.of(existente));
        when(prediosClientMock.obtenerDatosPredio(12L)).thenReturn(predioMock);
        when(facturaRepositoryMock.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Factura> optResultado = facturaService.actualizarFacturaParcial(1L, dto);

        assertTrue(optResultado.isPresent());
        Factura resultado = optResultado.get();
        assertEquals(50L, resultado.getNumFactura());
        assertEquals("Ganaderia", resultado.getGiro());
        assertEquals(350000.0, resultado.getMonto());
        assertEquals("OsornoNegro", resultado.getDireccion());
        verify(clientesClientMock, never()).obtenerDatosCliente(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar parcial evaluando la rama contraria de nulos para DTO de Clientes")
    void testActualizarFacturaParcial_RamaClienteExistentePredioNull() {
        Factura existente = new Factura();
        existente.setNumFactura(50L);

        FacturaDTO dto = new FacturaDTO(null, 5L, null, "Nuevo Giro", null);
        ClientesDTO clienteMock = new ClientesDTO("Cliente Parcial S.A.", "Santiago", "Santiago", "9999");

        when(facturaRepositoryMock.findById(1L)).thenReturn(Optional.of(existente));
        when(clientesClientMock.obtenerDatosCliente(5L)).thenReturn(clienteMock);
        when(facturaRepositoryMock.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Factura> optResultado = facturaService.actualizarFacturaParcial(1L, dto);

        assertTrue(optResultado.isPresent());
        assertEquals("Cliente Parcial S.A.", optResultado.get().getRazonSocial());
        assertEquals("Nuevo Giro", optResultado.get().getGiro());
        verify(prediosClientMock, never()).obtenerDatosPredio(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar parcial evaluando cuando numFactura y monto tambien se modifican en el DTO interno")
    void testActualizarFacturaParcial_CamposInternosModificados() {
        Factura existente = new Factura();

        FacturaDTO dto = new FacturaDTO(null, null, 888L, null, 12000.0);

        when(facturaRepositoryMock.findById(1L)).thenReturn(Optional.of(existente));
        when(facturaRepositoryMock.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Factura> optResultado = facturaService.actualizarFacturaParcial(1L, dto);

        assertTrue(optResultado.isPresent());
        assertEquals(888L, optResultado.get().getNumFactura());
        assertEquals(12000.0, optResultado.get().getMonto());
    }

    @Test
    @DisplayName("Debe retornar Optional.empty si la factura no existe al intentar actualizar parcial")
    void testActualizarFacturaParcial_NoExiste() {
        when(facturaRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        FacturaDTO dtoConCuerpo = new FacturaDTO(10L, 20L, 777L, "Giro Comercial", 15000.0);

        Optional<Factura> optResultado = facturaService.actualizarFacturaParcial(99L, dtoConCuerpo);

        assertTrue(optResultado.isEmpty());
        verify(facturaRepositoryMock, never()).save(any(Factura.class));
    }

    @Test
    @DisplayName("Debe invocar el borrado físico de la entidad al eliminar")
    void testEliminarFactura() {
        doNothing().when(facturaRepositoryMock).deleteById(1L);

        facturaService.eliminarFactura(1L);

        verify(facturaRepositoryMock, times(1)).deleteById(1L);
    }
}