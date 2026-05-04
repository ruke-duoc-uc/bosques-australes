package com.example.msfactura.service;
import com.example.msfactura.model.Factura;
import com.example.msfactura.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    public FacturaService (FacturaRepository facturaRepository){
        this.facturaRepository=facturaRepository;
    }

    public List<Factura> listarFactura(){
        return facturaRepository.findAll();
    }

    public Factura buscarPorId(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    public Factura guardarFactura(Factura factura){
        return facturaRepository.save(factura);
    }
}