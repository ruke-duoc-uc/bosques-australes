package com.example.seguridad.service;

import com.example.seguridad.model.*;
import com.example.seguridad.repository.SeguridadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Date;

@Service
public class AccidenteService {

    private final SeguridadRepository accidenteRepository;
    private final RestTemplate restTemplate; // Inyectamos el bean que creaste

    public AccidenteService(SeguridadRepository accidenteRepository, RestTemplate restTemplate) {
        this.accidenteRepository = accidenteRepository;
        this.restTemplate = restTemplate;
    }

    public Accidente registrar(Accidente accidente) {
        //VALIDACIÓN EXTERNA: Llamada al Micro de Trabajador
        try {
            String url = "http://localhost:8086/api/trabajadores/" + accidente.getTrabajadorId();
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

    public List<Accidente> listarTodos() {
        return accidenteRepository.findAll();
    }

    // ESTE FALTABA:
    public Accidente obtenerPorId(Long id) {
        return accidenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Accidente no encontrado con id: " + id));
    }

}