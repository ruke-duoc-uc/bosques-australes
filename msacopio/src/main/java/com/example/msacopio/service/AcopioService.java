package com.example.msacopio.service;
import com.example.msacopio.client.EspeciesClient;
import com.example.msacopio.client.EspeciesDTO;
import com.example.msacopio.model.AcopioModel;
import com.example.msacopio.repository.AcopioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("Acopio no encontrado con id: " + id));
    }

    public AcopioModel crear(AcopioModel acopio, Long idEspecies) {
        EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);
        AcopioModel acopioModel = new AcopioModel();
        acopioModel.setCodigoProducto(acopio.getCodigoProducto());
        acopioModel.setCantidadDisponible(acopio.getCantidadDisponible());
        acopioModel.setUnidadMedida(acopio.getUnidadMedida());
        acopioModel.setFechaIngreso(acopio.getFechaIngreso());

        //llamando a los de especies
        acopioModel.setIdEspecies(e.id());
        acopioModel.setNombreEspecies(e.nombre());
        return acopioRepository.save(acopioModel);
    }

    public Optional<AcopioModel> actualizar(Long id, Long idEspecies, AcopioModel datosNuevos) {
        EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);
        return acopioRepository.findById(id).map(acopioModel -> {
            acopioModel.setCodigoProducto(datosNuevos.getCodigoProducto());
            acopioModel.setCantidadDisponible(datosNuevos.getCantidadDisponible());
            acopioModel.setUnidadMedida(datosNuevos.getUnidadMedida());
            acopioModel.setFechaIngreso(datosNuevos.getFechaIngreso());

            acopioModel.setIdEspecies(e.id());
            acopioModel.setNombreEspecies(e.nombre());
            return acopioRepository.save(acopioModel);
        });
    }


    public void eliminar(Long id) {
        if (!acopioRepository.existsById(id)) {
            throw new RuntimeException("Acopio no encontrado con id: " + id);
        }
        acopioRepository.deleteById(id);
    }
}