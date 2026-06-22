package com.example.cliente.repository;

import com.example.cliente.model.Cliente;
import com.example.cliente.model.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente clienteActivo;
    private Cliente clienteInactivo;

    @BeforeEach
    void setUp() {
        // Cliente 1: Activo y con un nombre específico
        clienteActivo = new Cliente(
                "Forestal Valdivia S.A.", "77.345.678-9",
                "Sociedad Comercial Forestal Valdivia Limitada", "Esmeralda 450",
                "Valdivia", "Valdivia", "+56632221100",
                "finanzas@forvaldivia.cl", TipoCliente.EXPORTADOR, true // estado = true
        );

        // Cliente 2: Inactivo y con otro nombre para probar los filtros
        clienteInactivo = new Cliente(
                "Maderas del Sur", "88.123.456-k",
                "Maderas del Sur SPA", "Avenida Alemania 120",
                "Temuco", "Temuco", "+56452223344",
                "contacto@maderassur.cl", TipoCliente.PLANTA_PELLETS, false // estado = false
        );
    }

    @Test
    @DisplayName("Debe filtrar correctamente los clientes por su estado")
    void debeBuscarPorEstado() {
        // Arrange
        clienteRepository.save(clienteActivo);
        clienteRepository.save(clienteInactivo);

        // Act
        List<Cliente> activos = clienteRepository.findByEstado(true);
        List<Cliente> inactivos = clienteRepository.findByEstado(false);

        // Assert
        assertThat(activos).hasSize(1);
        assertThat(activos.get(0).getNombre()).isEqualTo("Forestal Valdivia S.A.");

        assertThat(inactivos).hasSize(1);
        assertThat(inactivos.get(0).getNombre()).isEqualTo("Maderas del Sur");
    }

    @Test
    @DisplayName("Debe buscar por nombre ignorando mayúsculas/minúsculas y por coincidencia parcial")
    void debeBuscarPorNombreContainingIgnoreCase() {
        // Arrange
        clienteRepository.save(clienteActivo); // Contiene "Valdivia"

        // Act & Assert
        // Prueba 1: Coincidencia exacta en minúsculas
        List<Cliente> resultadoMinis = clienteRepository.findByNombreContainingIgnoreCase("valdivia");
        assertThat(resultadoMinis).hasSize(1);

        // Prueba 2: Coincidencia parcial ("Forest")
        List<Cliente> resultadoParcial = clienteRepository.findByNombreContainingIgnoreCase("Forest");
        assertThat(resultadoParcial).hasSize(1);

        // Prueba 3: Palabra que no existe
        List<Cliente> resultadoVacio = clienteRepository.findByNombreContainingIgnoreCase("Inexistente");
        assertThat(resultadoVacio).isEmpty();
    }

    @Test
    @DisplayName("Debe verificar correctamente si un RUT ya existe en el sistema")
    void debeVerificarSiExisteByRut() {
        // Arrange
        clienteRepository.save(clienteActivo); // RUT: "77.345.678-9"

        // Act
        boolean existeRutRegistrado = clienteRepository.existsByRut("77.345.678-9");
        boolean noExisteRutFalso = clienteRepository.existsByRut("11.111.111-1");

        // Assert
        assertThat(existeRutRegistrado).isTrue();
        assertThat(noExisteRutFalso).isFalse();
    }
}
