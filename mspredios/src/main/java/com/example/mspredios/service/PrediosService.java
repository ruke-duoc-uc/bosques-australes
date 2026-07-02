package com.example.mspredios.service;
import com.example.mspredios.model.Predios;
import com.example.mspredios.model.PrediosDTO;
import com.example.mspredios.repository.PrediosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrediosService{

    private static final Logger log = LoggerFactory.getLogger(PrediosService.class);
    private final PrediosRepository prediosRepository;
    public PrediosService(PrediosRepository prediosRepository){
        this.prediosRepository = prediosRepository;
    }
    //Get
    public List<Predios> listarPredios(){
        log.info("[mspredios] Service - Listando todos los predios desde el repositorio");
        return prediosRepository.findAll();
    }
    public Predios buscarPorId(Long id){
        log.info("[mspredios] Service - Buscando predio por ID: {}", id);
        return prediosRepository.findById(id).orElse(null);
    }

    //Existe: Manejo de error 404 (no existe)
    public Boolean existePorId(Long id){
        log.info("[mspredios] Service - Verificando existencia de predio con ID: {}", id);
        return prediosRepository.existsById(id);
    }

    //Post
    public Predios guardarPredio(Predios predios){
        log.info("[mspredios] Service - Guardando nuevo predio en el repositorio");
        return prediosRepository.save(predios);
    }

    //Put
    public Optional<Predios> actualizarPredio(Long id, Predios predios){
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
    public Optional<?> actualizarParcialPredios(Long id, PrediosDTO prediosDTO){
        log.info("[mspredios] Service - Actualizando parcialmente predio con ID: {}", id);
        return prediosRepository.findById(id).map(predios->{
            if (prediosDTO.nombre() != null){
                predios.setNombre(prediosDTO.nombre());
            }
            if (prediosDTO.ciudad() != null){
                predios.setCiudad(prediosDTO.ciudad());
            }
            if (prediosDTO.comuna() != null){
                predios.setComuna(prediosDTO.comuna());
            }
            if (prediosDTO.direccion() != null){
                predios.setDireccion(prediosDTO.direccion());
            }
            return prediosRepository.save(predios);
        });
    }
    //Delete
    public void eliminarPredio(Long id){
        log.info("[mspredios] Service - Eliminando predio con ID: {}", id);
        prediosRepository.deleteById(id);
    }
}
