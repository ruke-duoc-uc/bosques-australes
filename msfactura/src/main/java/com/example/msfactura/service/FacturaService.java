package com.example.msfactura.service;
import com.example.msfactura.client.PrediosClient;
import com.example.msfactura.client.PrediosDTO;
import com.example.msfactura.model.Factura;
import com.example.msfactura.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final PrediosClient prediosClient;
    public FacturaService(FacturaRepository facturaRepository, PrediosClient prediosClient) {
        this.facturaRepository = facturaRepository;
        this.prediosClient = prediosClient;
    }
    public List<Factura> listarFactura(){
        return facturaRepository.findAll();
    }

    public Factura buscarPorId(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    public Factura crearFactura(Long Id, String descripcion) {

        PrediosDTO datosPredio = prediosClient.obtenerDatosPredio(Id);
        Factura nuevaFactura = new Factura();
        nuevaFactura.setDescripcion(descripcion);
        nuevaFactura.setIdPredio(datosPredio.id());
        nuevaFactura.setNombrePredio(datosPredio.nombre());
        return facturaRepository.save(nuevaFactura);
    }
}