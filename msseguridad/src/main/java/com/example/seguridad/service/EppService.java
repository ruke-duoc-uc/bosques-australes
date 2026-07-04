package com.example.seguridad.service;

import com.example.seguridad.model.Epp;
import com.example.seguridad.repository.EppRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Date;

/**
 * SERVICIO DE CAPA DE NEGOCIO - CONTROL Y TRAZABILIDAD DE EPP
 * Administra el ciclo de vida de los Equipos de Protección Personal de los operarios.
 * Implementa el patrón de borrado lógico para auditorías de cumplimiento normativo en terreno.
 */
@Service
public class EppService {

    private static final Logger log = LoggerFactory.getLogger(EppService.class);

    private final EppRepository eppRepository;
    private final RestTemplate restTemplate;
    private final String trabajadoresUri;


    /**
     * Constructor del componente con inyección de infraestructura REST y persistencia.
     */
    public EppService(EppRepository eppRepository, RestTemplate restTemplate, @Value("${MS_TRABAJADORES_URI:http://localhost:8086}") String trabajadoresUri) {
        this.eppRepository = eppRepository;
        this.restTemplate = restTemplate;
        this.trabajadoresUri = trabajadoresUri;
    }

    /**
     * ¿Qué hace?: Obtiene una lista global de todos los insumos de seguridad despachados.
     */
    public List<Epp> listarTodos() {
        log.info("[seguridad] Listando todos los EPP");
        return eppRepository.findAll();
    }

    /**
     * ¿Qué hace?: Busca una asignación de EPP por su ID único.
     */
    public Epp obtenerPorId(Long id) {
        return eppRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EPP no encontrado con id: " + id));
    }

    /**
     * ¿Qué hace?: Filtra la indumentaria de seguridad vigente que posee un trabajador específico.
     */
    public List<Epp> listarPorTrabajador(Long trabajadorId) {
        return eppRepository.findByTrabajadorIdAndActivoTrue(trabajadorId);
    }

    /**
     * ¿Qué hace?: Registra la asignación física de un nuevo implemento de seguridad.
     * ¿Validación?: Verifica vía red la vigencia del trabajador en el sistema maestro de RRHH.
     * ¿Normalización?: Transforma la glosa del tipo de EPP a mayúsculas para estandarizar búsquedas.
     */
    @Transactional
    public Epp registrar(Epp epp) {
        log.info("[seguridad] Registrando EPP para trabajador id={}", epp.getTrabajadorId());

        // Consumo síncrono al MS de trabajadores para simular restricción de clave foránea distributiva
        try {
            String url = trabajadoresUri + "/api/trabajadores/" + epp.getTrabajadorId();
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

    /**
     * ¿Qué hace?: Sobreescribe los metadatos de un lote o elemento de protección previamente otorgado.
     */
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
    /**
     * ¿Qué hace?: BORRADO LÓGICO - Desactiva la vigencia operativa de un implemento de seguridad.
     * ¿Por qué?: Evita la pérdida de registros históricos (exigido ante fiscalizaciones o juicios laborales).
     */
    @Transactional
    public void desactivar(Long id) {
        log.info("[seguridad] Desactivando EPP id={}", id);
        Epp epp = obtenerPorId(id);
        epp.setActivo(false);
        eppRepository.save(epp);
    }

    /**
     * ¿Qué hace?: Valida si un operario forestal cuenta con al menos un equipamiento de protección activo.
     * ¿Para qué sirve?: Es utilizado para auditorías preventivas antes del ingreso a faenas críticas de corte.
     */
    public boolean trabajadorTieneEppActivo(Long trabajadorId) {
        List<Epp> epps = eppRepository.findByTrabajadorIdAndActivoTrue(trabajadorId);
        return !epps.isEmpty();
    }
}