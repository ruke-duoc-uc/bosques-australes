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

/**
 * Contiene la lógica de negocio del microservicio de Despacho.
 * Se encarga de:
 *  - Operar sobre la base de datos local (a través de DespachoRepository).
 *  - Comunicarse con los microservicios externos de Especies y Factura
 *    (a través de RestClient) para obtener/validar datos relacionados.
 */

@Service //Marca esta clase como un componente de la capa de servicio, gestionado por Spring.
public class DespachoService {

    private final DespachoRepository despachoRepository;
    private final FacturaClient facturaClient;
    private final EspeciesClient especiesClient;

    //Inyección de dependencias por constructor.
    public DespachoService(DespachoRepository despachoRepository, FacturaClient facturaClient, EspeciesClient especiesClient) {
        this.despachoRepository = despachoRepository;
        this.facturaClient = facturaClient;
        this.especiesClient = especiesClient;
    }

    //Retorna todos los despachos guardados en la base de datos.
    public List<DespachoModel> listarTodos() {
        return this.despachoRepository.findAll();
    }

    //Busca un despacho por id. Si no existe, retorna null en vez de lanzar excepción
    //(posible mejora: lanzar EntityNotFoundException para que lo capture el GlobalExceptionsHandler).
    public DespachoModel buscarPorId(Long id) {
        return this.despachoRepository.findById(id).orElse(null);
    }

    //Guarda un nuevo despacho.
    //Nota: idEspecies e idFactura llegan como parámetros pero no se usan aquí para
    //consultar los microservicios externos (a diferencia de "actualizar"), por lo que
    //el despacho se guarda tal como llega en el body, sin cruzar datos de especie/factura.
    public DespachoModel guardar(DespachoModel despacho, Long idEspecies, Long idFactura) {
        return (DespachoModel)this.despachoRepository.save(despacho);
    }

    //Actualiza un despacho existente.
    public Optional<DespachoModel> actualizar(Long id, Long idEspecies, Long idFactura, DespachoModel despachoActualizado) {
        //1. Se consulta al microservicio de Especies para traer los datos actuales de la especie.
        EspeciesDTO e = this.especiesClient.obtenerDatosEspecies(idEspecies);
        //2. Se consulta al microservicio de Factura para traer los datos actuales de la factura.
        FacturaDTO f = this.facturaClient.obtenerDatosFactura(idFactura);

        //3. Se busca el despacho existente por id; si existe, se actualiza campo por campo.
        return this.despachoRepository.findById(id).map((despachoModel) -> {
            despachoModel.setFactura(f.numFactura());       //Se refresca el número de factura real.
            despachoModel.setEspecie(e.nombre());           //Se refresca el nombre real de la especie.
            despachoModel.setEstado(despachoActualizado.getEstado());
            despachoModel.setNombreDespachador(despachoActualizado.getNombreDespachador());
            despachoModel.setLugarRecepcion(despachoActualizado.getLugarRecepcion());
            despachoModel.setTipoPedido(despachoActualizado.getTipoPedido());
            despachoModel.setLocalidad(despachoActualizado.getLocalidad());
            despachoModel.setTrazabilidadCompleta(despachoActualizado.getTrazabilidadCompleta());
            //4. Se guarda el despacho ya actualizado en la base de datos.
            return (DespachoModel)this.despachoRepository.save(despachoModel);
        });
        //Si el despacho con ese id no existe, el Optional retornado estará vacío.
    }

    //Elimina un despacho por su id.
    public void eliminarDespacho(Long id) {
        this.despachoRepository.deleteById(id);
    }

    //Verifica si existe un despacho con el id dado (útil para validaciones antes de operar).
    public boolean existePorId(Long id) {
        return this.despachoRepository.existsById(id);
    }
}