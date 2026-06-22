package com.example.despachoo.service;

import com.example.despachoo.client.EspeciesClient;
import com.example.despachoo.client.EspeciesDTO;
import com.example.despachoo.client.FacturaClient;
import com.example.despachoo.client.FacturaDTO;
import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.repository.DespachoRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

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
        return despachoRepository.findAll();
    }

    public DespachoModel buscarPorId(Long id) {
        return despachoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El despacho con ID " + id + " no existe en el sistema."));
    }

    public DespachoModel guardar(DespachoModel despacho, Long idEspecies, Long idFactura) {
        return despachoRepository.save(despacho);
    }

    public DespachoModel actualizar(Long id, Long idEspecies, Long idFactura,
                                    DespachoModel despachoActualizado) {
        DespachoModel despachoModel = buscarPorId(id);
        EspeciesDTO e = especiesClient.obtenerDatosEspecies(idEspecies);
        FacturaDTO f = facturaClient.obtenerDatosFactura(idFactura);
        despachoModel.setFactura(f.numFactura());
        despachoModel.setEspecie(e.nombre());
        despachoModel.setEstado(despachoActualizado.getEstado());
        despachoModel.setNombreDespachador(despachoActualizado.getNombreDespachador());
        despachoModel.setLugarRecepcion(despachoActualizado.getLugarRecepcion());
        despachoModel.setTipoPedido(despachoActualizado.getTipoPedido());
        despachoModel.setLocalidad(despachoActualizado.getLocalidad());
        despachoModel.setTrazabilidadCompleta(despachoActualizado.getTrazabilidadCompleta());
        return despachoRepository.save(despachoModel);
    }
    public void eliminarDespacho(Long id) {
        DespachoModel despacho = buscarPorId(id); // reutiliza el método que ya valida existencia
        despachoRepository.delete(despacho);
    }
    public boolean existePorId(Long id) {
        return despachoRepository.existsById(id);
    }
}