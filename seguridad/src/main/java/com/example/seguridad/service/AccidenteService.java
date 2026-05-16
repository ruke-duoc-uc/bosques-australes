package com.example.seguridad.service;

import com.example.seguridad.model.*;
import com.example.seguridad.repository.SeguridadRepository;
import com.example.seguridad.dto.HabilitacionFaenaDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

@Service
public class AccidenteService {

    private final SeguridadRepository accidenteRepository;

    public AccidenteService(SeguridadRepository accidenteRepository) {
        this.accidenteRepository = accidenteRepository;
    }

    public List<Accidente> listarTodos() {
        return accidenteRepository.findAll();
    }

    // ESTE FALTABA:
    public Accidente obtenerPorId(Long id) {
        return accidenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Accidente no encontrado con id: " + id));
    }

    public Accidente registrar(Accidente accidente) {
        accidente.setFechaHoraRegistro(new Date().toString());

        if (accidente.getGravedad() == GravedadAccidente.GRAVE ||
                accidente.getGravedad() == GravedadAccidente.FATAL) {
            accidente.setFaenaBloqueada(true);
            accidente.setEstado(EstadoAccidente.INVESTIGANDO);
        } else {
            accidente.setFaenaBloqueada(false);
            accidente.setEstado(EstadoAccidente.PENDIENTE);
        }
        return accidenteRepository.save(accidente);
    }

    public Accidente habilitarFaena(Long id, HabilitacionFaenaDto dto) {
        Accidente accidente = obtenerPorId(id);
        accidente.setFaenaBloqueada(false);
        accidente.setEstado(EstadoAccidente.CERRADO);
        accidente.setSupervisorHabilitadorId(dto.getSupervisorHabilitadorId());
        accidente.setObservacionesHabilitacion(dto.getObservacionesHabilitacion());
        accidente.setFechaHabilitacion(new Date().toString());

        return accidenteRepository.save(accidente);
    }

    // ESTE DEBE LLAMARSE ASÍ PARA EL CONTROLLER:
    public boolean consultarSiEstaBloqueada(Long faenaId) {
        return accidenteRepository.existsByFaenaIdAndFaenaBloqueadaTrue(faenaId);
    }
}