package com.example.mscuadrilla.service;

import com.example.mscuadrilla.model.Cuadrilla;
import com.example.mscuadrilla.repository.CuadrillaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CuadrillaService {
    private static final Logger log = LoggerFactory.getLogger(CuadrillaService.class);
    private final CuadrillaRepository repository;
    private final RestClient restClient;

    public CuadrillaService(CuadrillaRepository repository, RestClient.Builder restClientBuilder, @Value("${MS_TRABAJADORES_URI:http://localhost:8086}") String baseUrl) {
        this.repository = repository;
        // La URL base apunta al controller de tu compañero
        this.restClient = restClientBuilder.baseUrl(baseUrl + "/api/trabajadores").build();
    }

    public List<Cuadrilla> listarTodas() {
        log.info("[cuadrillas] Listando todas las cuadrillas de operarios forestales");
        return repository.findAll();
    }

    public Cuadrilla guardar(Cuadrilla cuadrilla) {
        log.info("[cuadrillas] Creando nueva cuadrilla: {} en zona: {}", cuadrilla.getNombre(), cuadrilla.getZona());
        return repository.save(cuadrilla);
    }

    public Cuadrilla obtenerPorId(Long id) {
        log.info("[cuadrillas] Buscando cuadrilla ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cuadrilla con ID " + id + " no existe."));
    }

    public Cuadrilla actualizar(Long id, Cuadrilla datosNuevos) {
        log.info("[cuadrillas] Actualizando cuadrilla ID: {}", id);
        Cuadrilla existente = obtenerPorId(id);

        existente.setNombre(datosNuevos.getNombre());
        existente.setZona(datosNuevos.getZona());
        existente.setEspecialidad(datosNuevos.getEspecialidad());
        existente.setEstado(datosNuevos.getEstado());
        existente.setTrabajadoresIds(datosNuevos.getTrabajadoresIds());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        log.warn("[cuadrillas] Solicitud de eliminación para cuadrilla ID: {}", id);
        Cuadrilla existente = obtenerPorId(id); // Lanza 404 de inmediato si no existe
        repository.delete(existente);
    }

    public Map<String, Object> obtenerDetalleCuadrilla(Long id) {
        log.info("[cuadrillas-feign] Compilando detalle distribuido para cuadrilla ID: {}", id);
        Cuadrilla cuadrilla = obtenerPorId(id);

        List<Object> trabajadoresDetalle = new ArrayList<>();

        if (cuadrilla.getTrabajadoresIds() != null && !cuadrilla.getTrabajadoresIds().isEmpty()) {
            for (Long tId : cuadrilla.getTrabajadoresIds()) {
                try {
                    Map<String, Object> trabajador = restClient.get()
                            .uri("/{id}", tId)
                            .retrieve()
                            .body(Map.class);

                    if (trabajador != null) {
                        trabajadoresDetalle.add(trabajador);
                    }
                } catch (Exception e) {
                    log.error("[cuadrillas-rest] Error al conectar con MS Trabajadores para ID {}: {}", tId, e.getMessage());
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("id", tId);
                    errorMap.put("status", "No disponible (ID inválido o micro caído)");
                    trabajadoresDetalle.add(errorMap);
                }
            }
        }

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
