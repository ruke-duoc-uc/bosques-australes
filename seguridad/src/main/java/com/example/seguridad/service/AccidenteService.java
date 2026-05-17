package com.example.seguridad.service;

import com.example.seguridad.model.*;
import com.example.seguridad.repository.SeguridadRepository;
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
            accidente.setEstado(EstadoAccidente.INVESTIGANDO);
        } else {
            accidente.setEstado(EstadoAccidente.PENDIENTE);
        }
        return accidenteRepository.save(accidente);
    }
}