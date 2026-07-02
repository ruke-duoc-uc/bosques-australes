package com.example.despachoo.service;

import com.example.despachoo.client.EspeciesClient;
import com.example.despachoo.client.EspeciesDTO;
import com.example.despachoo.client.FacturaClient;
import com.example.despachoo.client.FacturaDTO;
import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.repository.DespachoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DespachoService {
    private final DespachoRepository despachoRepository;
    private final FacturaClient facturaClient;
    private final EspeciesClient especiesClient;

    public DespachoService(DespachoRepository despachoRepository, FacturaClient facturaClient, EspeciesClient especiesClient) {
        this.despachoRepository = despachoRepository;
        this.facturaClient = facturaClient;
        this.especiesClient = especiesClient;
    }

    public List<DespachoModel> listarTodos() {
        return this.despachoRepository.findAll();
    }

    public DespachoModel buscarPorId(Long id) {
        return this.despachoRepository.findById(id).orElse(null);
    }

    public DespachoModel guardar(DespachoModel despacho, Long idEspecies, Long idFactura) {
        return (DespachoModel)this.despachoRepository.save(despacho);
    }

    public Optional<DespachoModel> actualizar(Long id, Long idEspecies, Long idFactura, DespachoModel despachoActualizado) {
        EspeciesDTO e = this.especiesClient.obtenerDatosEspecies(idEspecies);
        FacturaDTO f = this.facturaClient.obtenerDatosFactura(idFactura);
        return this.despachoRepository.findById(id).map((despachoModel) -> {
            despachoModel.setFactura(f.numFactura());
            despachoModel.setEspecie(e.nombre());
            despachoModel.setEstado(despachoActualizado.getEstado());
            despachoModel.setNombreDespachador(despachoActualizado.getNombreDespachador());
            despachoModel.setLugarRecepcion(despachoActualizado.getLugarRecepcion());
            despachoModel.setTipoPedido(despachoActualizado.getTipoPedido());
            despachoModel.setLocalidad(despachoActualizado.getLocalidad());
            despachoModel.setTrazabilidadCompleta(despachoActualizado.getTrazabilidadCompleta());
            return (DespachoModel)this.despachoRepository.save(despachoModel);
        });
    }

    public void eliminarDespacho(Long id) {
        this.despachoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return this.despachoRepository.existsById(id);
    }
}
