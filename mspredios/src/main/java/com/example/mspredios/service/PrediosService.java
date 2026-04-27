package com.example.mspredios.service;

import com.example.mspredios.model.Predios;
import com.example.mspredios.repository.PrediosRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PrediosService{

    private final PrediosRepository prediosRepository;
    public PrediosService(PrediosRepository prediosRepository){
       this.prediosRepository = prediosRepository;
    }
    public List<Predios> listarPredios(){
        return prediosRepository.findAll();
    }
    public Predios buscarPorId(Long id){
        return prediosRepository.findById(id).orElse(null);
    }
    public Predios guardarPredio(Predios predios){
        return prediosRepository.save(predios);
    }
}
