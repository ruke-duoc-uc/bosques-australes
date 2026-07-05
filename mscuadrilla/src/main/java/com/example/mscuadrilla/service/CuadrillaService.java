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

/**
 * SERVICIO DE CAPA DE NEGOCIO PARA LA GESTIÓN DE CUADRILLAS
 * Contiene las reglas operativas para la administración de los equipos en terreno.
 * Actúa como un cliente HTTP distribuido utilizando 'RestClient' de Spring Boot 3 para
 * conectarse de forma síncrona con el microservicio de Trabajadores y unificar la información.
 */
@Service
public class CuadrillaService {
    // Componente para registrar auditoría de eventos y errores en la consola del servidor
    private static final Logger log = LoggerFactory.getLogger(CuadrillaService.class);
    // Repositorio para transacciones directas en la base de datos local de cuadrillas
    private final CuadrillaRepository repository;
    // Cliente HTTP de Spring para consumir endpoints de APIs externas de forma reactiva/síncrona
    private final RestClient restClient;

    /**
     * Constructor del servicio que configura e inyecta las dependencias.
     * Utiliza la anotación @Value para extraer dinámicamente la URL base del microservicio de trabajadores
     * (por ejemplo, desde variables de entorno en Render o Docker), dejando un fallback por defecto en localhost:8086.
     */
    public CuadrillaService(CuadrillaRepository repository, RestClient.Builder restClientBuilder, @Value("${MS_TRABAJADORES_URI:http://localhost:8086}") String baseUrl) {
        this.repository = repository;
        // La URL base apunta al controller de tu compañero
        this.restClient = restClientBuilder.baseUrl(baseUrl + "/api/trabajadores").build();
    }

    /**
     * ¿Qué hace?: Recupera todas las cuadrillas registradas localmente en la base de datos.
     * ¿Qué entrega?: Una lista (List) completa de objetos de tipo Cuadrilla.
     */
    public List<Cuadrilla> listarTodas() {
        log.info("[cuadrillas] Listando todas las cuadrillas de operarios forestales");
        return repository.findAll();
    }

    /**
     * ¿Qué hace?: Registra una nueva cuadrilla en la persistencia local.
     * ¿Qué recibe?: Un objeto 'Cuadrilla' con los datos validados del JSON de entrada.
     * ¿Qué entrega?: La entidad 'Cuadrilla' almacenada con su ID correspondiente.
     */
    public Cuadrilla guardar(Cuadrilla cuadrilla) {
        log.info("[cuadrillas] Creando nueva cuadrilla: {} en zona: {}", cuadrilla.getNombre(), cuadrilla.getZona());
        return repository.save(cuadrilla);
    }

    /**
     * ¿Qué hace?: Busca una cuadrilla en la base de datos utilizando su llave primaria.
     * ¿Qué recibe?: El ID (Long) único del equipo.
     * ¿Qué entrega?: La entidad 'Cuadrilla' encontrada.
     * @throws EntityNotFoundException Si el identificador no existe en los registros (Genera un HTTP 404).
     */
    public Cuadrilla obtenerPorId(Long id) {
        log.info("[cuadrillas] Buscando cuadrilla ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cuadrilla con ID " + id + " no existe."));
    }

    /**
     * ¿Qué hace?: Modifica las propiedades de una cuadrilla existente.
     * ¿Qué recibe?: El ID a alterar y el objeto con las nuevas especificaciones de reemplazo.
     * ¿Qué entrega?: El registro 'Cuadrilla' actualizado y guardado en la base de datos.
     */
    public Cuadrilla actualizar(Long id, Cuadrilla datosNuevos) {
        log.info("[cuadrillas] Actualizando cuadrilla ID: {}", id);
        Cuadrilla existente = obtenerPorId(id);// Primero asegura su existencia, si no, interrumpe con un 404

        // Mapea los valores actualizados sobre la entidad persistente
        existente.setNombre(datosNuevos.getNombre());
        existente.setZona(datosNuevos.getZona());
        existente.setEspecialidad(datosNuevos.getEspecialidad());
        existente.setEstado(datosNuevos.getEstado());
        existente.setTrabajadoresIds(datosNuevos.getTrabajadoresIds());

        return repository.save(existente);
    }

    /**
     * ¿Qué hace?: Elimina de forma física el registro de una cuadrilla de la base de datos.
     * ¿Qué recibe?: El ID (Long) del equipo a destruir.
     */
    public void eliminar(Long id) {
        log.warn("[cuadrillas] Solicitud de eliminación para cuadrilla ID: {}", id);
        Cuadrilla existente = obtenerPorId(id); // Lanza 404 de inmediato si no existe
        repository.delete(existente);
    }

    /**
     * ¿Qué hace?: ARQUITECTURA DISTRIBUIDA - Compila los datos locales de la cuadrilla y realiza
     * llamadas HTTP en bucle hacia el microservicio externo para adjuntar el perfil completo de cada trabajador.
     * ¿Qué recibe?: El ID de la cuadrilla a detallar.
     * ¿Qué entrega?: Un Mapa relacional (Map) estructurado con el formato del JSON final que incluye los datos de los operarios.
     */
    public Map<String, Object> obtenerDetalleCuadrilla(Long id) {
        log.info("[cuadrillas-feign] Compilando detalle distribuido para cuadrilla ID: {}", id);
        // 1. Busca los datos básicos de la cuadrilla en la BD local
        Cuadrilla cuadrilla = obtenerPorId(id);

        List<Object> trabajadoresDetalle = new ArrayList<>();

        // 2. Si la cuadrilla cuenta con IDs en su lista de trabajadores, inicia el consumo distribuido
        if (cuadrilla.getTrabajadoresIds() != null && !cuadrilla.getTrabajadoresIds().isEmpty()) {
            for (Long tId : cuadrilla.getTrabajadoresIds()) {
                try {
                    // Realiza una petición GET HTTP a: {baseUrl}/api/trabajadores/{id}
                    Map<String, Object> trabajador = restClient.get()
                            .uri("/{id}", tId)
                            .retrieve()
                            .body(Map.class);// Mapea el JSON recibido directamente a un mapa genérico

                    if (trabajador != null) {
                        trabajadoresDetalle.add(trabajador);// Adjunta el trabajador al listado de éxito
                    }
                } catch (Exception e) {
                    // CONTROL DE TOLERANCIA A FALLOS: Si el microservicio de trabajadores falla o no encuentra el ID,
                    // el sistema no se cae, captura el error y genera un objeto temporal informando la anomalía.
                    log.error("[cuadrillas-rest] Error al conectar con MS Trabajadores para ID {}: {}", tId, e.getMessage());
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("id", tId);
                    errorMap.put("status", "No disponible (ID inválido o micro caído)");
                    trabajadoresDetalle.add(errorMap);
                }
            }
        }

        // 3. Integra la información local con la lista de objetos recolectada del exterior
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
