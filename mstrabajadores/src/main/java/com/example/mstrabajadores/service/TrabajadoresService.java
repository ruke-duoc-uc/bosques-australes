package com.example.mstrabajadores.service;
import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.repository.TrabajadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import com.example.mstrabajadores.exception.NegocioException;

import java.util.List;


/**
 * Contiene la lógica de negocio del microservicio de Trabajadores.
 * A diferencia de despachoo/msacopio, este microservicio no depende de
 * otros microservicios externos: toda la lógica es local, sobre su propia
 * base de datos de trabajadores.
 */
@Service
public class TrabajadoresService {

    //Inyección por campo. Cumple la misma función que la inyección por
    //constructor usada en los otros microservicios, solo que aquí Spring
    //asigna la dependencia directamente sobre el atributo.
    @Autowired
    private TrabajadoresRepository repository;

    //Retorna todos los trabajadores registrados.
    public List<TrabajadoresModel> getAll() {
        return repository.findAll();
    }

    //Busca un trabajador por id. Si no existe, lanza EntityNotFoundException,
    //que el GlobalExceptionHandler convierte en un 404.
    public TrabajadoresModel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El trabajador con ID " + id + " no existe en el sistema."));
    }

    // Registra un nuevo trabajador.
    public TrabajadoresModel save(TrabajadoresModel trabajador) {
        //Regla de negocio: no se permite más de un trabajador con el mismo RUT.
        //Si ya existe, se lanza una NegocioException con status 409 (Conflict),
        //que el GlobalExceptionHandler traduce a la respuesta HTTP correspondiente.
        if (repository.existsByRut(trabajador.getRut())) {
            throw new NegocioException(
                    "Ya existe un trabajador registrado con el RUT " + trabajador.getRut(), 409);
        }
        return repository.save(trabajador);
    }

    //Elimina un trabajador por id.
    public void delete(Long id) {
        TrabajadoresModel trabajador = getById(id); // valida existencia, lanza 404 si no existe
        repository.delete(trabajador);
    }

    //Actualiza los datos de un trabajador existente.
    public TrabajadoresModel actualizar(Long id, TrabajadoresModel datosNuevos) {
        TrabajadoresModel trabajadorExistente = getById(id); // lanza 404 si no existe

        //Se actualizan todos los campos con los nuevos datos recibidos.
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