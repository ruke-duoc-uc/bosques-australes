package com.example.msacopio.service;
import com.example.msacopio.model.AcopioModel;
import com.example.msacopio.repository.AcopioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class AcopioService {
    private final AcopioRepository acopioRepository;
    private final RestClient restClient;

    private static final String ESPECIE_SERVICE_URL = "http://localhost:8087";

    public AcopioService(AcopioRepository acopioRepository) {
        this.acopioRepository = acopioRepository;
        this.restClient = RestClient.create(ESPECIE_SERVICE_URL);
    }

    public List<AcopioModel> listarTodos() {
        return acopioRepository.findAll();
    }

    public AcopioModel buscarPorId(Long id) {
        return acopioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acopio no encontrado con id: " + id));
    }

    public AcopioModel crear(AcopioModel acopio) {
        Map especie = restClient.get()
                .uri("/api/especies/{id}", acopio.getIdEspecies())
                .retrieve()
                .body(Map.class);
        if (especie == null) {
            throw new RuntimeException("Especie no encontrada con id: " + acopio.getIdEspecies());
        }
        acopio.setNombreEspecies((String) especie.get("nombreEspecies"));
        return acopioRepository.save(acopio);
    }

    public AcopioModel actualizar(Long id, AcopioModel datosNuevos) {
        AcopioModel existente = buscarPorId(id);

        existente.setCodigoProducto(datosNuevos.getCodigoProducto());
        existente.setCantidadDisponible(datosNuevos.getCantidadDisponible());
        existente.setUnidadMedida(datosNuevos.getUnidadMedida());
        existente.setFechaIngreso(datosNuevos.getFechaIngreso());
        existente.setIdEspecies(datosNuevos.getIdEspecies());
        existente.setNombreEspecies(datosNuevos.getNombreEspecies());

        return acopioRepository.save(existente);
    }
    public void eliminar(Long id) {
        if (!acopioRepository.existsById(id)) {
            throw new RuntimeException("Acopio no encontrado con id: " + id);
        }
        acopioRepository.deleteById(id);
    }
}