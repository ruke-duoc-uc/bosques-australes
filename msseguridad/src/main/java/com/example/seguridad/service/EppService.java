package com.example.seguridad.service;

import com.example.seguridad.model.Epp;
import com.example.seguridad.repository.EppRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Date;

@Service
public class EppService {

    private static final Logger log = LoggerFactory.getLogger(EppService.class);

    private final EppRepository eppRepository;
    private final RestTemplate restTemplate;


    public EppService(EppRepository eppRepository, RestTemplate restTemplate) {
        this.eppRepository = eppRepository;
        this.restTemplate = restTemplate;
    }

    public List<Epp> listarTodos() {
        log.info("[seguridad] Listando todos los EPP");
        return eppRepository.findAll();
    }

    public Epp obtenerPorId(Long id) {
        return eppRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EPP no encontrado con id: " + id));
    }

    public List<Epp> listarPorTrabajador(Long trabajadorId) {
        return eppRepository.findByTrabajadorIdAndActivoTrue(trabajadorId);
    }

    @Transactional
    public Epp registrar(Epp epp) {
        log.info("[seguridad] Registrando EPP para trabajador id={}", epp.getTrabajadorId());

        try {
            String url = "http://localhost:8086/api/trabajadores/" + epp.getTrabajadorId();
            // Si el trabajador no existe, esto lanzará una excepción
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            log.error("Error de validación: El trabajador {} no existe", epp.getTrabajadorId());
            throw new RuntimeException("No se puede registrar el EPP: El trabajador con ID "
                    + epp.getTrabajadorId() + " no existe en el sistema de RRHH.");
        }

        // Al usar Strings, eliminamos la validación isBefore para evitar errores de compilación.
        // El sistema confía en el String enviado.
        epp.setActivo(true);
        epp.setTipo(epp.getTipo().toUpperCase());

        return eppRepository.save(epp);
    }

    @Transactional
    public Epp actualizar(Long id, Epp eppActualizado) {
        log.info("[seguridad] Actualizando EPP id={}", id);
        Epp eppExistente = obtenerPorId(id);

        eppExistente.setTipo(eppActualizado.getTipo().toUpperCase());
        eppExistente.setFechaEntrega(eppActualizado.getFechaEntrega());
        eppExistente.setFechaVencimiento(eppActualizado.getFechaVencimiento());
        eppExistente.setObservaciones(eppActualizado.getObservaciones());

        return eppRepository.save(eppExistente);
    }

    // Eliminación lógica: marcamos como no activo en lugar de borrar
    @Transactional
    public void desactivar(Long id) {
        log.info("[seguridad] Desactivando EPP id={}", id);
        Epp epp = obtenerPorId(id);
        epp.setActivo(false);
        eppRepository.save(epp);
    }

    /**
     * Verifica si el trabajador tiene algún EPP marcado como activo.
     * Simplificado para funcionar solo con el estado Activo (Boolean).
     */
    public boolean trabajadorTieneEppActivo(Long trabajadorId) {
        List<Epp> epps = eppRepository.findByTrabajadorIdAndActivoTrue(trabajadorId);
        return !epps.isEmpty();
    }
}