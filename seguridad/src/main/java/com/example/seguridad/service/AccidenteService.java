package com.example.seguridad.service;

import com.example.seguridad.model.*;
import com.example.seguridad.repository.SeguridadRepository;
import com.example.seguridad.dto.HabilitacionFaenaDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date; // Usamos la clásica de Java que es un Stringer fácil

@Service
public class AccidenteService {

    private final SeguridadRepository accidenteRepository;

    public AccidenteService(SeguridadRepository accidenteRepository) {
        this.accidenteRepository = accidenteRepository;
    }

    public List<Accidente> listarTodos() {
        return accidenteRepository.findAll();
    }

    public Accidente registrar(Accidente accidente) {
        // Seteamos la fecha de registro como un String simple
        accidente.setFechaHoraRegistro(new Date().toString());

        // R5: Lógica de bloqueo basada en el Enum de gravedad
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
        Accidente accidente = accidenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No encontrado"));

        accidente.setFaenaBloqueada(false);
        accidente.setEstado(EstadoAccidente.CERRADO);
        accidente.setSupervisorHabilitadorId(dto.getSupervisorHabilitadorId());
        accidente.setObservacionesHabilitacion(dto.getObservacionesHabilitacion());

        // Fecha de habilitación como String
        accidente.setFechaHabilitacion(new Date().toString());

        return accidenteRepository.save(accidente);
    }
}