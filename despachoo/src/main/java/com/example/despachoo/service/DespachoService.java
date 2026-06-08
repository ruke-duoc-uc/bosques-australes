package com.example.despachoo.service;

import com.example.despachoo.client.EspeciesClient;
import com.example.despachoo.client.EspeciesDTO;
import com.example.despachoo.client.FacturaClient;
import com.example.despachoo.client.FacturaDTO;
import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.repository.DespachoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespachoService {

    private final DespachoRepository despachoRepository;

    private final FacturaClient facturaClient;
    private final EspeciesClient especiesClient;

    public DespachoService(DespachoRepository despachoRepository,
                           FacturaClient facturaClient, EspeciesClient especiesClient) {
        this.despachoRepository = despachoRepository;
        this.facturaClient = facturaClient;
        this.especiesClient = especiesClient;
    }

    public List<DespachoModel> listarTodos() {
        try {
            return despachoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar despachos: " + e.getMessage());
        }
    }

    public DespachoModel buscarPorId(Long id) {
        try {
            return despachoRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al  buscar despacho con id" + id + ":" + e.getMessage());
        }
    }

    public DespachoModel guardar(DespachoModel despacho) {
        try {
            return despachoRepository.save(despacho);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar despacho: " + e.getMessage());
        }
    }

    public Optional<DespachoModel> actualizar(Long id, Long idEspecies, Long idFactura,
                                              DespachoModel despachoActualizado) {
        try {
            EspeciesDTO e = especiesClient.obtenerDatosEspecies(idEspecies);
            FacturaDTO f = facturaClient.obtenerDatosFactura(idFactura);
            return despachoRepository.findById(id).map(despachoModel -> {
                despachoModel.setFactura(f.numFactura());
                despachoModel.setEspecie(e.nombre());
                despachoModel.setEstado(despachoActualizado.getEstado());
                despachoModel.setNombreDespachador(despachoActualizado.getNombreDespachador());
                despachoModel.setLugarRecepcion(despachoActualizado.getLugarRecepcion());
                despachoModel.setTipoPedido(despachoActualizado.getTipoPedido());
                despachoModel.setLocalidad(despachoActualizado.getLocalidad());
                despachoModel.setTrazabilidadCompleta(despachoActualizado.getTrazabilidadCompleta());
                return despachoRepository.save(despachoModel);
            });
        } catch (Exception ex) {
            throw new RuntimeException("Error al actualizar despacho: " + ex.getMessage());
        }
    }
    public void eliminarDespacho(Long id){
        try {
            despachoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar despacho con id " + id + ":" + e.getMessage());
        }
    }
    public boolean existePorId(Long id){
        try {
            return despachoRepository.existsById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar despacho con id" + id + ":" + e.getMessage());
        }
    }
}