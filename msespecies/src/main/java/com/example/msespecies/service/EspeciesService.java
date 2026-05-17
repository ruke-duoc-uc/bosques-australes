package com.example.msespecies.service;

import com.example.msespecies.model.Especies;
import com.example.msespecies.repository.EspeciesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspeciesService {
    private final EspeciesRepository especiesRepository;
    public EspeciesService(EspeciesRepository especiesRepository){
        this.especiesRepository = especiesRepository;
    }

    public List<Especies> listarEspecies(){
        return especiesRepository.findAll();
    }
    public Especies buscarPorId(Long id){
        return especiesRepository.findById(id).orElse(null);
    }

    public Boolean existePorId(Long id) {
        return especiesRepository.existsById(id);
    }

    public Especies guardarEspecie(Especies especies){
        return especiesRepository.save(especies);
    }

    public Optional<Especies> actualizarEspecie(Long id, Especies especieActualizada){
        return especiesRepository.findById(id).map(especies -> {
            especies.setNombre(especieActualizada.getNombre());
            especies.setUso(especieActualizada.getUso());
            especies.setCalidad(especieActualizada.getCalidad());
            especies.setColor(especieActualizada.getColor());
            return especiesRepository.save(especies);
        });
    }

    public void eliminarEspecie(Long id){
        especiesRepository.deleteById(id);
    }
}
