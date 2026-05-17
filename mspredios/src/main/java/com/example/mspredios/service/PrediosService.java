package com.example.mspredios.service;
import com.example.mspredios.model.Predios;
import com.example.mspredios.repository.PrediosRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrediosService{

    private final PrediosRepository prediosRepository;
    public PrediosService(PrediosRepository prediosRepository){
        this.prediosRepository = prediosRepository;
    }
    //Get
    public List<Predios> listarPredios(){
        return prediosRepository.findAll();
    }
    public Predios buscarPorId(Long id){
        return prediosRepository.findById(id).orElse(null);
    }

    //Existe: Manejo de error 404 (no existe)
    public Boolean existePorId(Long id){
        return prediosRepository.existsById(id);
    }

    //Post
    public Predios guardarPredio(Predios predios){
        return prediosRepository.save(predios);
    }

    //Put
    public Optional<Predios> actualizarPredio(Long id, Predios predios){
        return prediosRepository.findById(id).map(prediosNuevo -> {
            prediosNuevo.setComuna(predios.getComuna());
            prediosNuevo.setCiudad(predios.getCiudad());
            prediosNuevo.setDueno(predios.getDueno());
            prediosNuevo.setNombre(predios.getNombre());
            return prediosRepository.save(prediosNuevo);
        });
    }

    //Delete
    public void eliminarPredio(Long id){
        prediosRepository.deleteById(id);
    }
}
