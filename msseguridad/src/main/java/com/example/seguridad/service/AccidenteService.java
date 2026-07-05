package com.example.seguridad.service;

import com.example.seguridad.model.*;
import com.example.seguridad.repository.SeguridadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * SERVICIO DE CAPA DE NEGOCIO - GESTIÓN DE ACCIDENTABILIDAD
 * Orquesta la lógica para el reporte y clasificación de incidentes laborales en las faenas.
 * Implementa validaciones distribuidas hacia el módulo de RRHH para asegurar la consistencia de datos.
 */
@Service
public class AccidenteService {

    private final SeguridadRepository accidenteRepository;
    private final RestTemplate restTemplate; // Inyectamos el bean que creaste
    private final String trabajadoresUri;

    /**
     * Inyección de dependencias y configuración dinámica del endpoint de Recursos Humanos.
     */
    public AccidenteService(SeguridadRepository accidenteRepository, RestTemplate restTemplate, @Value("${MS_TRABAJADORES_URI:http://localhost:8086}") String trabajadoresUri) {
        this.accidenteRepository = accidenteRepository;
        this.restTemplate = restTemplate;
        this.trabajadoresUri = trabajadoresUri;
    }

    /**
     * ¿Qué hace?: Registra un siniestro en el sistema bajo estrictas reglas de negocio operacionales.
     * ¿Regla Distribuida?: Realiza una llamada síncrona preventiva al microservicio de trabajadores.
     * Si el ID no existe en RRHH, aborta la operación arrojando una excepción.
     * ¿Regla de Negocio?: Clasifica de forma automática el estado del caso según el nivel de severidad.
     */
    public Accidente registrar(Accidente accidente) {
        //VALIDACIÓN EXTERNA: Llamada al Micro de Trabajador
        try {
            String url = trabajadoresUri + "/api/trabajadores/" + accidente.getTrabajadorId();
            // Si el trabajador no existe, RestTemplate lanzará una excepción automáticamente
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("No se puede registrar accidente: El Trabajador con ID "
                    + accidente.getTrabajadorId() + " no existe en el sistema de RRHH.");
        }

        // LÓGICA DE REGISTRO INTERNA
        accidente.setFechaHoraRegistro(new Date().toString());

        if (accidente.getGravedad() == GravedadAccidente.GRAVE ||
                accidente.getGravedad() == GravedadAccidente.FATAL) {
            accidente.setEstado(EstadoAccidente.INVESTIGANDO);
        } else {
            accidente.setEstado(EstadoAccidente.PENDIENTE);
        }

        return accidenteRepository.save(accidente);
    }

    /**
     * ¿Qué hace?: Recupera el histórico total de accidentes registrados en la compañía.
     */
    public List<Accidente> listarTodos() {
        return accidenteRepository.findAll();
    }
    /**
     * ¿Qué hace?: Obtiene un reporte específico utilizando su llave primaria.
     * @throws EntityNotFoundException Si el registro del siniestro no es hallado.
     */

    public Accidente obtenerPorId(Long id) {
        return accidenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Accidente no encontrado con id: " + id));
    }

}