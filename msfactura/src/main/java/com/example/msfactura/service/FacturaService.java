package com.example.msfactura.service;
import com.example.msfactura.client.ClientesClient;
import com.example.msfactura.client.ClientesDTO;
import com.example.msfactura.client.PrediosClient;
import com.example.msfactura.client.PrediosDTO;
import com.example.msfactura.model.Factura;
import com.example.msfactura.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final PrediosClient prediosClient;
    private final ClientesClient clientesClient;
    public FacturaService(FacturaRepository facturaRepository,
                          PrediosClient prediosClient,
                          ClientesClient clientesClient) {
        this.facturaRepository = facturaRepository;
        this.prediosClient = prediosClient;
        this.clientesClient = clientesClient;
    }
    public List<Factura> listarFactura(){
        return facturaRepository.findAll();
    }

    public Factura buscarPorId(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    public Factura crearFactura(Long idPredio, Long idCliente, Long numFactura, String giro, Double monto) {
        PrediosDTO prediosDTO = prediosClient.obtenerDatosPredio(idPredio);
        ClientesDTO clientesDTO = clientesClient.obtenerDatosCliente(idCliente);
        // 2. Crear y poblar la entidad
        Factura nueva = new Factura();
        // Datos Factura
        nueva.setNumFactura(numFactura);
        nueva.setGiro(giro);
        nueva.setMonto(monto);
        // Datos del Predio
        nueva.setIdPredio(prediosDTO.id());
        nueva.setNombrePredio(prediosDTO.nombre());
        nueva.setDireccion(prediosDTO.ubicacion());
        // Datos del Cliente
        nueva.setRazonSocial(clientesDTO.razonSocial());
        nueva.setComuna(clientesDTO.comuna());
        nueva.setTelefonoCliente(clientesDTO.telefono());
        nueva.setCiudad(clientesDTO.ciudad());
        return facturaRepository.save(nueva);
    }
    public Optional<Factura> actualizarFactura(Long id, Factura facturaActualizada){
        return facturaRepository.findById(id).map(factura -> {
            factura.setNumFactura(factura.getNumFactura());
            factura.setGiro(factura.getGiro());
            factura.setMonto(factura.getMonto());
            return facturaRepository.save(factura);
        });
    }
    public void eliminarFactura(Long id){
        facturaRepository.deleteById(id);
    }
    public Boolean existePorId(Long id){return facturaRepository.existsById(id);}
}