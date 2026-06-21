package com.example.msfactura.service;

import com.example.msfactura.client.ClientesClient;
import com.example.msfactura.client.ClientesDTO;
import com.example.msfactura.client.PrediosClient;
import com.example.msfactura.client.PrediosDTO;
import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
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
    // GET
    public List<Factura> listarFactura(){
        return facturaRepository.findAll();
    }
    public Factura buscarPorId(Long id){
        return facturaRepository.findById(id).orElse(null);
    }
    // POST
    public Factura guardarFactura(Long idPredio, Long idCliente, Factura factura) {
        PrediosDTO prediosDTO = prediosClient.obtenerDatosPredio(idPredio);
        ClientesDTO clientesDTO = clientesClient.obtenerDatosCliente(idCliente);
        // 2. Crear y poblar la entidad
        Factura nueva = new Factura();
        // Datos Factura
        nueva.setNumFactura(factura.getNumFactura());
        nueva.setGiro(factura.getGiro());
        nueva.setMonto(factura.getMonto());
        // Datos del Predio
        nueva.setNombrePredio(prediosDTO.nombre());
        nueva.setDireccion(prediosDTO.ciudad()+prediosDTO.comuna());
        // Datos del Cliente
        nueva.setRazonSocial(clientesDTO.razonSocial());
        nueva.setComuna(clientesDTO.comuna());
        nueva.setTelefonoCliente(clientesDTO.telefono());
        nueva.setCiudad(clientesDTO.ciudad());
        return facturaRepository.save(nueva);
    }
    // PUT
    public Optional<Factura> actualizarFacturaCompleta(Long id,
                                                       Long idPredio,
                                                       Long idCliente,
                                                       Factura facturaActualizada){
        PrediosDTO prediosDTO = prediosClient.obtenerDatosPredio(idPredio);
        ClientesDTO clientesDTO = clientesClient.obtenerDatosCliente(idCliente);
        return facturaRepository.findById(id).map(factura -> {
            factura.setNumFactura(facturaActualizada.getNumFactura());
            factura.setGiro(facturaActualizada.getGiro());
            factura.setMonto(facturaActualizada.getMonto());
            //Datos del Predio
            factura.setNombrePredio(prediosDTO.nombre());
            factura.setDireccion(prediosDTO.ciudad()+prediosDTO.comuna());
            //Datos del Cliente
            factura.setRazonSocial(clientesDTO.razonSocial());
            factura.setComuna(clientesDTO.comuna());
            factura.setTelefonoCliente(clientesDTO.telefono());
            factura.setCiudad(clientesDTO.ciudad());
            return facturaRepository.save(factura);
        });
    }
    // PATCH
    public Optional<Factura> actualizarFacturaParcial(Long id,
                                                      FacturaDTO facturaDTO){
        return facturaRepository.findById(id).map(factura -> {
            if (facturaDTO.numFactura() != null){
                factura.setNumFactura(facturaDTO.numFactura());
            }
            if (facturaDTO.giro() != null){
                factura.setGiro(facturaDTO.giro());
            }
            if (facturaDTO.monto() != null){
                factura.setMonto(facturaDTO.monto());
            }
            if (facturaDTO.idPredio() != null){
                PrediosDTO prediosDTO = prediosClient.obtenerDatosPredio(facturaDTO.idPredio());
                factura.setNombrePredio(prediosDTO.nombre());
                factura.setDireccion(prediosDTO.ciudad()+prediosDTO.comuna());
            }
            if (facturaDTO.idCLiente() != null){
                ClientesDTO clientesDTO = clientesClient.obtenerDatosCliente(facturaDTO.idCLiente());
                factura.setRazonSocial(clientesDTO.razonSocial());
                factura.setComuna(clientesDTO.comuna());
                factura.setTelefonoCliente(clientesDTO.telefono());
                factura.setCiudad(clientesDTO.ciudad());
            }
            return facturaRepository.save(factura);
        });
    }
    public void eliminarFactura(Long id){
        facturaRepository.deleteById(id);
    }
    public Boolean existePorId(Long id){return facturaRepository.existsById(id);}
}