package com.example.mstrabajadores.service;
import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.repository.TrabajadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadoresService {
    //Esto reemplaza la declaracion "final" de java
    @Autowired
    private TrabajadoresRepository repository;

    public List <TrabajadoresModel> listarTrabajadores(){
     return repository.findAll();
    }

    public Optional<TrabajadoresModel> buscarPorId(Long id){
        return repository.findById(id);
    }
    public Boolean existePorId(Long id){
        return repository.existsById(id);
    }
    public TrabajadoresModel guardarTrabajador(TrabajadoresModel trabajador){
        return repository.save(trabajador);
    }
    public void eliminarTrabajador(Long id){
        repository.deleteById(id);
    }
    public Optional<TrabajadoresModel> actualizarTrabajador(Long id, TrabajadoresModel nuevoTrabajador){
        return repository.findById(id).map(trabajadoresModel ->{
            trabajadoresModel.setNombre(nuevoTrabajador.getNombre());
            trabajadoresModel.setRut(nuevoTrabajador.getRut());
            trabajadoresModel.setEstado(nuevoTrabajador.getEstado());
            trabajadoresModel.setEdad(nuevoTrabajador.getEdad());
            trabajadoresModel.setTelefono(nuevoTrabajador.getTelefono());
            trabajadoresModel.setCorreo(nuevoTrabajador.getCorreo());
            trabajadoresModel.setFechaContrato(nuevoTrabajador.getFechaContrato());
            return repository.save(trabajadoresModel);
        });
    }
}
