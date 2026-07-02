package com.example.msacopio.service;

import jakarta.persistence.EntityNotFoundException;
import com.example.msacopio.client.EspeciesClient;
import com.example.msacopio.client.EspeciesDTO;
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
    private final EspeciesClient especiesClient;

    private static final String ESPECIE_SERVICE_URL = "http://localhost:8087";

    public AcopioService(AcopioRepository acopioRepository, EspeciesClient especiesClient) {
        this.acopioRepository = acopioRepository;
        this.especiesClient = especiesClient;
        this.restClient = RestClient.create(ESPECIE_SERVICE_URL);
    }

    public List<AcopioModel> listarTodos() {
        return acopioRepository.findAll();
    }

    public AcopioModel buscarPorId(Long id) {
        return acopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El acopio con ID " + id + " no existe en el sistema."));
    }

    public AcopioModel crear(AcopioModel acopio, Long idEspecies) {
        EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);
        AcopioModel acopioModel = new AcopioModel();
        acopioModel.setCodigoProducto(acopio.getCodigoProducto());
        acopioModel.setCantidadDisponible(acopio.getCantidadDisponible());
        acopioModel.setUnidadMedida(acopio.getUnidadMedida());
        acopioModel.setFechaIngreso(acopio.getFechaIngreso());

        // llamando a los de especies
        acopioModel.setIdEspecies(e.id());
        acopioModel.setNombreEspecies(e.nombre());

        return acopioRepository.save(acopioModel);
    }

    public AcopioModel actualizar(Long id, Long idEspecies, AcopioModel datosNuevos) {
        AcopioModel acopioModel = buscarPorId(id);
        EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);
        acopioModel.setCodigoProducto(datosNuevos.getCodigoProducto());
        acopioModel.setCantidadDisponible(datosNuevos.getCantidadDisponible());
        acopioModel.setUnidadMedida(datosNuevos.getUnidadMedida());
        acopioModel.setFechaIngreso(datosNuevos.getFechaIngreso());
        acopioModel.setIdEspecies(e.id());
        acopioModel.setNombreEspecies(e.nombre());
        return acopioRepository.save(acopioModel);
    }


    public void eliminar(Long id) {
        AcopioModel acopio = buscarPorId(id);
        acopioRepository.delete(acopio);
    }
}