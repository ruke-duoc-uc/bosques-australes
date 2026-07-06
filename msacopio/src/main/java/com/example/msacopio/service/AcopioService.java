package com.example.msacopio.service;

import jakarta.persistence.EntityNotFoundException;
import com.example.msacopio.client.EspeciesClient;
import com.example.msacopio.client.EspeciesDTO;
import com.example.msacopio.model.AcopioModel;
import com.example.msacopio.repository.AcopioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

/**
 * Contiene la lógica de negocio del microservicio de Acopio.
 * Se encarga de:
 *  - Operar sobre la base de datos local (a través de AcopioRepository).
 *  - Comunicarse con el microservicio de Especies para completar/validar
 *    los datos de la especie asociada a cada acopio.
 */
@Service
public class AcopioService {
    private final AcopioRepository acopioRepository;
    private final RestClient restClient;
    private final EspeciesClient especiesClient;

    //URL fija del microservicio de Especies (hardcodeada, a diferencia de EspeciesClient
    //que usa @Value con un valor configurable vía application.properties).
    private static final String ESPECIE_SERVICE_URL = "http://localhost:8087";

    public AcopioService(AcopioRepository acopioRepository, EspeciesClient especiesClient) {
        this.acopioRepository = acopioRepository;
        this.especiesClient = especiesClient;
        //Se crea un RestClient adicional apuntando a ESPECIE_SERVICE_URL,
        //aunque en la práctica no se usa en ningún método de esta clase
        //(toda la comunicación real se hace a través de "especiesClient").
        this.restClient = RestClient.create(ESPECIE_SERVICE_URL);
    }

    //Retorna todos los acopios registrados.
    public List<AcopioModel> listarTodos() {
        return acopioRepository.findAll();
    }

    //Busca un acopio por id. Si no existe, lanza EntityNotFoundException,
    //que será capturada por el GlobalExceptionHandler y convertida en un 404.
    public AcopioModel buscarPorId(Long id) {
        return acopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El acopio con ID " + id + " no existe en el sistema."));
    }

    //Crea un nuevo acopio.
    public AcopioModel crear(AcopioModel acopio, Long idEspecies) {
        //1. Se consulta al microservicio de Especies para validar que la especie existe
        //y para obtener sus datos reales (id y nombre).
        EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);

        //2. Se arma un nuevo AcopioModel "limpio" copiando solo los datos propios
        //que vienen en el body (no se reutiliza directamente el objeto "acopio"
        //recibido, para evitar guardar datos de especie que el cliente pudiera
        // haber mandado incorrectos).
        AcopioModel acopioModel = new AcopioModel();
        acopioModel.setCodigoProducto(acopio.getCodigoProducto());
        acopioModel.setCantidadDisponible(acopio.getCantidadDisponible());
        acopioModel.setUnidadMedida(acopio.getUnidadMedida());
        acopioModel.setFechaIngreso(acopio.getFechaIngreso());

        //llamando a los de especies
        //3. Se completan los datos de especie con la info real obtenida del microservicio.
        acopioModel.setIdEspecies(e.id());
        acopioModel.setNombreEspecies(e.nombre());

        return acopioRepository.save(acopioModel);
    }

    //Actualiza un acopio existente.
    public AcopioModel actualizar(Long id, Long idEspecies, AcopioModel datosNuevos) {
        //1. Se busca el acopio existente (lanza 404 si no existe, gracias a buscarPorId).
        AcopioModel acopioModel = buscarPorId(id);
        //2. Se valida/obtiene la especie actualizada.
        EspeciesDTO e = especiesClient.obtenerDatosCliente(idEspecies);

        // 3. Se actualizan todos los campos con los nuevos datos recibidos.
        acopioModel.setCodigoProducto(datosNuevos.getCodigoProducto());
        acopioModel.setCantidadDisponible(datosNuevos.getCantidadDisponible());
        acopioModel.setUnidadMedida(datosNuevos.getUnidadMedida());
        acopioModel.setFechaIngreso(datosNuevos.getFechaIngreso());
        acopioModel.setIdEspecies(e.id());
        acopioModel.setNombreEspecies(e.nombre());

        return acopioRepository.save(acopioModel);
    }

    //Elimina un acopio existente (lanza 404 si no existe, gracias a buscarPorId).
    public void eliminar(Long id) {
        AcopioModel acopio = buscarPorId(id);
        acopioRepository.delete(acopio);
    }
}