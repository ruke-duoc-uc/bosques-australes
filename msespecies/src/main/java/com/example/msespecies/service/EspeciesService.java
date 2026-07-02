package com.example.msespecies.service;

import com.example.msespecies.model.Especies;
import com.example.msespecies.model.EspeciesDTO;
import com.example.msespecies.repository.EspeciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspeciesService {
    private static final Logger log = LoggerFactory.getLogger(EspeciesService.class);
    private final EspeciesRepository especiesRepository;
    public EspeciesService(EspeciesRepository especiesRepository){
        this.especiesRepository = especiesRepository;
    }

    public List<Especies> listarEspecies(){
        log.info("[msespecies] Service - Listando todas las especies desde el repositorio");
        return especiesRepository.findAll();
    }
    public Especies buscarPorId(Long id){
        log.info("[msespecies] Service - Buscando especie por ID: {}", id);
        return especiesRepository.findById(id).orElse(null);
    }

    public Boolean existePorId(Long id) {
        log.info("[msespecies] Service - Verificando existencia de especie con ID: {}", id);
        return especiesRepository.existsById(id);
    }

    public Especies guardarEspecie(Especies especies){
        log.info("[msespecies] Service - Guardando nueva especie en el repositorio");
        return especiesRepository.save(especies);
    }

    public Optional<Especies> actualizarEspecie(Long id, Especies especieActualizada){
        log.info("[msespecies] Service - Actualizando especie completa con ID: {}", id);
        return especiesRepository.findById(id).map(especies -> {
            especies.setNombre(especieActualizada.getNombre());
            especies.setUso(especieActualizada.getUso());
            especies.setCalidad(especieActualizada.getCalidad());
            especies.setColor(especieActualizada.getColor());
            return especiesRepository.save(especies);
        });
    }
    // PATCH
    public Optional<?> actualizarParcialEspecie (Long id, EspeciesDTO especiesActualzada){
        log.info("[msespecies] Service - Actualizando parcialmente especie con ID: {}", id);
        return especiesRepository.findById(id).map(especies->{
            if (especiesActualzada.nombre() != null){
                especies.setNombre(especiesActualzada.nombre());
            }
            if (especiesActualzada.uso() != null){
                especies.setUso(especiesActualzada.uso());
            }
            if (especiesActualzada.calidad() != null){
                especies.setCalidad(especiesActualzada.calidad());
            }
            if (especiesActualzada.color() != null){
                especies.setColor(especiesActualzada.color());
            }
            return especiesRepository.save(especies);
        });
    }
    // DELETE
    public void eliminarEspecie(Long id){
        log.info("[msespecies] Service - Eliminando especie con ID: {}", id);
        especiesRepository.deleteById(id);
    }
}
