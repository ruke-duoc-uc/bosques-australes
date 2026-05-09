package com.example.seguridad.service;
import com.example.seguridad.model.*;
import com.example.seguridad.repository.SeguridadRepository;
import com.example.seguridad.dto.HabilitacionFaenaDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EppService {

    private static final Logger log = LoggerFactory.getLogger(EppService.class);

    private final EppRepository eppRepository;

    public EppService(EppRepository eppRepository) {
        this.eppRepository = eppRepository;
    }

    public List<Epp> listarTodos() {
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
    public Epp registrar(EppRequestDTO dto) {
        log.info("[ms-seguridad] Registrando EPP tipo={} para trabajador id={}",
                dto.getTipo(), dto.getTrabajadorId());

        if (dto.getFechaVencimiento().isBefore(dto.getFechaEntrega())) {
            throw new NegocioException(
                    "La fecha de vencimiento no puede ser anterior a la fecha de entrega.", 422);
        }

        Epp epp = new Epp();
        epp.setTrabajadorId(dto.getTrabajadorId());
        epp.setTipo(dto.getTipo().toUpperCase());
        epp.setFechaEntrega(dto.getFechaEntrega());
        epp.setFechaVencimiento(dto.getFechaVencimiento());
        epp.setObservaciones(dto.getObservaciones());
        epp.setActivo(true);

        Epp guardado = eppRepository.save(epp);
        log.info("[ms-seguridad] EPP registrado id={}", guardado.getId());
        return guardado;
    }

    @Transactional
    public Epp actualizar(Long id, EppRequestDTO dto) {
        log.info("[ms-seguridad] Actualizando EPP id={}", id);
        Epp epp = obtenerPorId(id);

        if (dto.getFechaVencimiento().isBefore(dto.getFechaEntrega())) {
            throw new NegocioException(
                    "La fecha de vencimiento no puede ser anterior a la fecha de entrega.", 422);
        }

        epp.setTipo(dto.getTipo().toUpperCase());
        epp.setFechaEntrega(dto.getFechaEntrega());
        epp.setFechaVencimiento(dto.getFechaVencimiento());
        epp.setObservaciones(dto.getObservaciones());
        return eppRepository.save(epp);
    }

    // Eliminación lógica: no borra el registro, solo lo desactiva
    @Transactional
    public void desactivar(Long id) {
        log.info("[ms-seguridad] Desactivando EPP id={}", id);
        Epp epp = obtenerPorId(id);
        epp.setActivo(false);
        eppRepository.save(epp);
    }

    /**
     * Verifica si el trabajador tiene EPP activos y todos vigentes.
     * Usado en el endpoint enriquecido del controller.
     */
    public boolean trabajadorTieneEppVigente(Long trabajadorId) {
        List<Epp> epps = eppRepository.findByTrabajadorIdAndActivoTrue(trabajadorId);
        if (epps.isEmpty()) return false;
        return epps.stream().allMatch(e -> e.getFechaVencimiento().isAfter(LocalDate.now()));
    }
}
