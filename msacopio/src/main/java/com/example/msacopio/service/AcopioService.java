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
        try {
            return acopioRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar acopios: " + e.getMessage());
        }
    }

    public AcopioModel buscarPorId(Long id) {
        try {
            return acopioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Acopio no encontrado con id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar acopio por id " + id + ":" + e.getMessage());
        }
    }

    public AcopioModel crear(AcopioModel acopio, Long idEspecies) {
        AcopioModel acopioModel;
        try {
            EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);
            acopioModel = new AcopioModel();
            acopioModel.setCodigoProducto(acopio.getCodigoProducto());
            acopioModel.setCantidadDisponible(acopio.getCantidadDisponible());
            acopioModel.setUnidadMedida(acopio.getUnidadMedida());
            acopioModel.setFechaIngreso(acopio.getFechaIngreso());

            //llamando a los de especies
            acopioModel.setIdEspecies(e.id());
            acopioModel.setNombreEspecies(e.nombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear acopio con especie id " + idEspecies + ":" + e.getMessage());
        }
        return acopioRepository.save(acopioModel);
    }

    public Optional<AcopioModel> actualizar(Long id, Long idEspecies, AcopioModel datosNuevos) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar acopio con id " + id + ": " + e.getMessage());
        }
    }


       public void eliminar(Long id) {
           try {
               if (!acopioRepository.existsById(id)) {
                   throw new RuntimeException("Acopio no encontrado con id: " + id);
               }
               acopioRepository.deleteById(id);
           } catch (Exception e) {
               throw new RuntimeException("Error al eliminar acopio con id " + id + ": " + e.getMessage());
           }
       }
}