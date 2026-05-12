package com.example.msfactura.service;
import com.example.msfactura.client.ClientesClient;
import com.example.msfactura.client.ClientesDTO;
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
        // 1. Obtener datos de los Microservicios (Records)
        PrediosDTO p = prediosClient.obtenerDatosPredio(idPredio);
        ClientesDTO c = clientesClient.obtenerDatosCliente(idCliente);

        // 2. Crear y poblar la entidad
        Factura nueva = new Factura();

        // Datos básicos
        nueva.setFactura(numFactura);
        nueva.setGiro(giro);
        nueva.setMonto(monto);

        // Datos del Predio (desde el record PrediosDTO)
        nueva.setIdPredio(p.id());
        nueva.setNombrePredio(p.nombre()); // o p.nombre() según tu record
        nueva.setDireccion(p.ubicacion());

        // Datos del Cliente (desde el record ClientesDTO)
        nueva.setRazonSocial(c.razonSocial());
        nueva.setComuna(c.comuna());
        nueva.setTelefonoCliente(c.telefono());

        // Nota: Si el MS Cliente devuelve 'comuna' pero no 'ciudad',
        // podrías usar la comuna como ciudad o asegurar que el DTO traiga ambos.
        nueva.setCiudad(c.comuna());

        return facturaRepository.save(nueva);
    }
            /*
        // DTO Predios
        nuevaFactura.setIdPredio(datosPredio.id());
        nuevaFactura.setNombrePredio(datosPredio.nombre());
        nuevaFactura.setDireccion(datosPredio.direccion());
        // DTO Clientes
        nuevaFactura.setRazonSocial(datosCliente.razonSocial());
        nuevaFactura.setCiudad(datosCliente.comuna()+" Dato: Ciudad");
        nuevaFactura.setComuna(datosCliente.comuna());
        nuevaFactura.setTelefonoCliente(datosCliente.telefono());

        */
}