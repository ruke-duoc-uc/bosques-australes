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
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar trabajadores: " + e.getMessage());
        }
    }

    public Optional<TrabajadoresModel> getById(Long id){
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar trabajador con id " + id + ":" + e.getMessage());
        }
    }

    public TrabajadoresModel save(TrabajadoresModel trabajador){
        try {
            return repository.save(trabajador);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar trabajador: " + e.getMessage());
        }
    }

    public void delete(Long id){
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar trabajador con id" + id + ":" + e.getMessage());
        }
    }
}
