package com.example.msfactura.service;

import com.example.msfactura.client.ClientesClient;
import com.example.msfactura.client.ClientesDTO;
import com.example.msfactura.client.PrediosClient;
import com.example.msfactura.client.PrediosDTO;
import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
import com.example.msfactura.repository.FacturaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    @Service deriva el manejo de esta clase
    a Spring, permitiendo su uso en el controller
 */
@Service
public class FacturaService {
    private static final Logger log = LoggerFactory.getLogger(FacturaService.class);
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
    // Este metodo obtiene todos los objetos almacenados en la base de datos del microservicio
    public List<Factura> listarFactura(){
        log.info("[msfactura] Service - Listando todas las facturas desde el repositorio");
        return facturaRepository.findAll();
    }
    // Este metodo obtiene un objeto almacenado en la base de datos del microservicio
    // usando su ID para encontrarlo
    public Factura buscarPorId(Long id){
        log.info("[msfactura] Service - Buscando factura por ID: {}", id);
        return facturaRepository.findById(id).orElse(null);
    }
    // POST
    // Este metodo permite crear una nueva factura
    // Nesecita un cuerpo con los datos propios de factura, ademas de dar la ID del cliente y predio
    // correspondientes para la consulta a traves de "client"
    public Factura guardarFactura(Long idPredio, Long idCliente, Factura factura) {
        log.info("[msfactura] Service - Guardando nueva factura vinculada a Predio ID: {} y Cliente ID: {}", idPredio, idCliente);
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
    // Permite una actualizacion completa de los datos en la factura
    // Exige todos los atributos de factura, ademas de los ID de el cliente y predio
    public Optional<Factura> actualizarFacturaCompleta(Long id,
                                                       Long idPredio,
                                                       Long idCliente,
                                                       Factura facturaActualizada){
        log.info("[msfactura] Service - Actualizando factura completa con ID: {}, Predio ID: {}, Cliente ID: {}", id, idPredio, idCliente);
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
    // Permite la actualizacion parcial de los datos de una factura
    /*
        Debido al uso de FacturaDTO, las ID se da en el cuerpo,
        esto permite que se pueda actualizar solo el cliente, solo el predio
        o solo los atributos
    */

    public Optional<Factura> actualizarFacturaParcial(Long id,
                                                      FacturaDTO facturaDTO){
        log.info("[msfactura] Service - Actualizando parcialmente factura con ID: {}", id);
        return facturaRepository.findById(id).map(factura -> {
            // Datos Factura
            if (facturaDTO.numFactura() != null){
                factura.setNumFactura(facturaDTO.numFactura());
            }
            if (facturaDTO.giro() != null){
                factura.setGiro(facturaDTO.giro());
            }
            if (facturaDTO.monto() != null){
                factura.setMonto(facturaDTO.monto());
            }
            // Si se otorga un ID en el cuerpo, se actualizan todos los datos
            // Datos Predio
            if (facturaDTO.idPredio() != null){
                PrediosDTO prediosDTO = prediosClient.obtenerDatosPredio(facturaDTO.idPredio());
                factura.setNombrePredio(prediosDTO.nombre());
                factura.setDireccion(prediosDTO.ciudad()+prediosDTO.comuna());
            }
            // Datos Cliente
            if (facturaDTO.idCliente() != null){
                ClientesDTO clientesDTO = clientesClient.obtenerDatosCliente(facturaDTO.idCliente());
                factura.setRazonSocial(clientesDTO.razonSocial());
                factura.setComuna(clientesDTO.comuna());
                factura.setTelefonoCliente(clientesDTO.telefono());
                factura.setCiudad(clientesDTO.ciudad());
            }
            return facturaRepository.save(factura);
        });
    }
    // DELETE
    // Este metodo permite eliminar una factura de la base de datos del microservicio
    public void eliminarFactura(Long id){
        log.info("[msfactura] Service - Eliminando factura con ID: {}", id);
        facturaRepository.deleteById(id);
    }
    // Este metodo comprueba a nivel aplicacion que exista una factura
    public Boolean existePorId(Long id){
        log.info("[msfactura] Service - Verificando existencia de factura con ID: {}", id);
        return facturaRepository.existsById(id);
    }
}
