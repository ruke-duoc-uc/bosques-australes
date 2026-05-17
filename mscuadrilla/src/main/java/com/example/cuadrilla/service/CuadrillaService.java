package com.example.cuadrilla.service;

import com.example.cuadrilla.model.Cuadrilla;
import com.example.cuadrilla.repository.CuadrillaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CuadrillaService {
    private final CuadrillaRepository repository;
    private final RestClient restClient;

    public CuadrillaService(CuadrillaRepository repository, RestClient.Builder restClientBuilder) {
        this.repository = repository;
        // La URL base apunta al controller de tu compañero
        this.restClient = restClientBuilder.baseUrl("http://localhost:8086/api/trabajadores").build();
    }

    public List<Cuadrilla> listarTodas() {
        return repository.findAll();
    }

    public Cuadrilla guardar(Cuadrilla cuadrilla) {
        return repository.save(cuadrilla);
    }

    public Cuadrilla actualizar(Long id, Cuadrilla datosNuevos) {
        return repository.findById(id).map(cuadrilla -> {
            cuadrilla.setNombre(datosNuevos.getNombre());
            cuadrilla.setZona(datosNuevos.getZona());
            cuadrilla.setEspecialidad(datosNuevos.getEspecialidad());
            cuadrilla.setEstado(datosNuevos.getEstado());
            // Sincronizamos la lista de IDs de trabajadores
            cuadrilla.setTrabajadoresIds(datosNuevos.getTrabajadoresIds());
            return repository.save(cuadrilla);
        }).orElseThrow(() -> new RuntimeException("Cuadrilla no encontrada con ID: " + id));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Map<String, Object> obtenerDetalleCuadrilla(Long id) {
        // 1. Buscamos la cuadrilla en nuestra base de datos local
        Cuadrilla cuadrilla = repository.findById(id).orElse(null);

        // Si la cuadrilla no existe, devolvemos null (el Controller enviará un 404)
        if (cuadrilla == null) {
            return null;
        }

        // 2. Preparamos la lista donde guardaremos los datos que traeremos del otro microservicio
        List<Object> trabajadoresDetalle = new ArrayList<>();

        // Verificamos si la cuadrilla tiene IDs de trabajadores asociados
        if (cuadrilla.getTrabajadoresIds() != null && !cuadrilla.getTrabajadoresIds().isEmpty()) {

            // Por cada ID guardado, consultamos al microservicio de Trabajadores (Puerto 8086)
            for (Long tId : cuadrilla.getTrabajadoresIds()) {
                try {
                    // Hacemos la petición GET http://localhost:8086/api/trabajadores/{id}
                    Map<String, Object> trabajador = restClient.get()
                            .uri("/{id}", tId)
                            .retrieve()
                            .body(Map.class);

                    if (trabajador != null) {
                        trabajadoresDetalle.add(trabajador);
                    }
                } catch (Exception e) {
                    // Si el micro del compañero falla o el ID no existe, capturamos el error
                    // Imprimimos en consola para debuguear si es culpa de la URL o conexión
                    System.err.println("Error al obtener trabajador " + tId + ": " + e.getMessage());

                    // Agregamos un mapa informativo para que el JSON final no salga vacío
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("id", tId);
                    errorMap.put("status", "Error de conexión o ID no encontrado en micro trabajadores");
                    trabajadoresDetalle.add(errorMap);
                }
            }
        }

        // 3. Construimos la respuesta final combinando nuestros datos con los externos
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", cuadrilla.getId());
        respuesta.put("nombre", cuadrilla.getNombre());
        respuesta.put("zona", cuadrilla.getZona());
        respuesta.put("especialidad", cuadrilla.getEspecialidad());
        respuesta.put("estado", cuadrilla.getEstado());
        respuesta.put("trabajadores", trabajadoresDetalle);

        return respuesta;
    }
}