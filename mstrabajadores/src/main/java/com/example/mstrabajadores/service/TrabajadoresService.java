package com.example.mstrabajadores.service;
import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.repository.TrabajadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import com.example.mstrabajadores.exception.NegocioException;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadoresService {

    @Autowired
    private TrabajadoresRepository repository;

    public List<TrabajadoresModel> getAll() {
        return repository.findAll();
    }

    public TrabajadoresModel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El trabajador con ID " + id + " no existe en el sistema."));
    }

    public TrabajadoresModel save(TrabajadoresModel trabajador) {
        if (repository.existsByRut(trabajador.getRut())) {
            throw new NegocioException(
                    "Ya existe un trabajador registrado con el RUT " + trabajador.getRut(), 409);
        }
        return repository.save(trabajador);
    }

    public void delete(Long id) {
        TrabajadoresModel trabajador = getById(id); // valida existencia, lanza 404 si no existe
        repository.delete(trabajador);
    }

    public TrabajadoresModel actualizar(Long id, TrabajadoresModel datosNuevos) {
        TrabajadoresModel trabajadorExistente = getById(id); // lanza 404 si no existe

        trabajadorExistente.setNombre(datosNuevos.getNombre());
        trabajadorExistente.setRut(datosNuevos.getRut());
        trabajadorExistente.setEstado(datosNuevos.getEstado());
        trabajadorExistente.setEdad(datosNuevos.getEdad());
        trabajadorExistente.setTelefono(datosNuevos.getTelefono());
        trabajadorExistente.setCorreo(datosNuevos.getCorreo());
        trabajadorExistente.setCargo(datosNuevos.getCargo());
        trabajadorExistente.setFechaContrato(datosNuevos.getFechaContrato());

        return repository.save(trabajadorExistente);
    }
}
