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
        return despachoRepository.findAll();
    }

    public DespachoModel buscarPorId(Long id) {
        return despachoRepository.findById(id).orElse(null);
    }

    public DespachoModel guardar(DespachoModel despacho) {
        return despachoRepository.save(despacho);
    }

    public Optional<DespachoModel> actualizar(Long id, Long idEspecies, Long idFactura,
                                              DespachoModel despachoActualizado) {
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
    }
    public void eliminarDespacho(Long id){
        despachoRepository.deleteById(id);
    }
    public boolean existePorId(Long id){return despachoRepository.existsById(id);
    }
}