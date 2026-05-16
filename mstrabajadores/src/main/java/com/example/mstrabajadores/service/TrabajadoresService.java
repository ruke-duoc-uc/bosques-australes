package com.example.mstrabajadores.service;
import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.repository.TrabajadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadoresService {

    @Autowired
    private TrabajadoresRepository repository;

    public List <TrabajadoresModel> getAll(){
     return repository.findAll();
    }

    public Optional<TrabajadoresModel> getById(Long id){
        return repository.findById(id);
    }

    public TrabajadoresModel save(TrabajadoresModel trabajador){
        return repository.save(trabajador);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
