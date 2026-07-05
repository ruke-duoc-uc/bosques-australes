package com.example.mspredios.service;

import com.example.mspredios.model.Predios;
import com.example.mspredios.model.PrediosDTO;
import com.example.mspredios.repository.PrediosRepository;
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
public class PrediosService {

    private static final Logger log = LoggerFactory.getLogger(PrediosService.class);
    private final PrediosRepository prediosRepository;

    public PrediosService(PrediosRepository prediosRepository) {
        this.prediosRepository = prediosRepository;
    }

    // GET
    // Este metodo obtiene todos los objetos almacenados en la base de datos del microservicio
    public List<Predios> listarPredios() {
        log.info("[mspredios] Service - Listando todos los predios desde el repositorio");
        return prediosRepository.findAll();
    }

    // Este metodo obtiene un objeto almacenado en la base de datos del microservicio
    // usando su ID para encontrarlo
    public Predios buscarPorId(Long id) {
        log.info("[mspredios] Service - Buscando predio por ID: {}", id);
        return prediosRepository.findById(id).orElse(null);
    }

    // Este metodo comprueba a nivel aplicacion que exista un predio
    public Boolean existePorId(Long id) {
        log.info("[mspredios] Service - Verificando existencia de predio con ID: {}", id);
        return prediosRepository.existsById(id);
    }

    // POST
    // Este metodo permite crear un nuevo predio
    public Predios guardarPredio(Predios predios) {
        log.info("[mspredios] Service - Guardando nuevo predio en el repositorio");
        return prediosRepository.save(predios);
    }

    // PUT
    // Permite una actualizacion completa de los datos en el predio
    public Optional<Predios> actualizarPredio(Long id, Predios predios) {
        log.info("[mspredios] Service - Actualizando predio completo con ID: {}", id);
        return prediosRepository.findById(id).map(prediosNuevo -> {
            prediosNuevo.setComuna(predios.getComuna());
            prediosNuevo.setCiudad(predios.getCiudad());
            prediosNuevo.setDireccion(predios.getDireccion());
            prediosNuevo.setNombre(predios.getNombre());
            return prediosRepository.save(prediosNuevo);
        });
    }

    // PATCH
    // Permite la actualizacion parcial de los datos de un predio
    /*
        Debido al uso de PrediosDTO, las ID se da en el cuerpo,
        esto permite que se pueda actualizar solo el cliente, solo el predio
        o solo los atributos
     */
    public Optional<?> actualizarParcialPredios(Long id, PrediosDTO prediosDTO) {
        log.info("[mspredios] Service - Actualizando parcialmente predio con ID: {}", id);
        return prediosRepository.findById(id).map(predios -> {
            if (prediosDTO.nombre() != null) {
                predios.setNombre(prediosDTO.nombre());
            }
            if (prediosDTO.ciudad() != null) {
                predios.setCiudad(prediosDTO.ciudad());
            }
            if (prediosDTO.comuna() != null) {
                predios.setComuna(prediosDTO.comuna());
            }
            if (prediosDTO.direccion() != null) {
                predios.setDireccion(prediosDTO.direccion());
            }
            return prediosRepository.save(predios);
        });
    }

    // DELETE
    // Este metodo permite eliminar un predio de la base de datos del microservicio
    public void eliminarPredio(Long id) {
        log.info("[mspredios] Service - Eliminando predio con ID: {}", id);
        prediosRepository.deleteById(id);
    }
}
