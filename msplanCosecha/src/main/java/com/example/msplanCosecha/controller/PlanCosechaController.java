package com.example.msplanCosecha.controller;

import com.example.msplanCosecha.model.PlanCosechaDTO;
import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.service.PlanCosechaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// Avisa a Spring que esta clase es el controller
@RestController
// Da el cuerpo base para usar los métodos de este microservicio
@RequestMapping("/api/planCosecha")
// Da un nombre al conjunto de métodos presentes en este controller
@Tag(name = "Controller / Plan Cosecha", description = "Métodos para interactuar con el msplanCosecha")
public class PlanCosechaController {
    private static final Logger log = LoggerFactory.getLogger(PlanCosechaController.class);
    private final PlanCosechaService planCosechaService;

    public PlanCosechaController(PlanCosechaService planCosechaService){
        this.planCosechaService = planCosechaService;
    }

    // GET
    // @GetMapping permite que el método solo responda a una solicitud GET
    @GetMapping
    // @Operation Describe el método en Swagger, dando un resumen y descripción
    @Operation(summary = "Listar Planes de cosecha", description = "Obtiene todos los planes de cosecha")
    // Configura las respuestas de Swagger ante ciertos códigos
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<?> listarPlanCosecha(){
        log.info("[msplanCosecha] GET /api/planCosecha - Listando todos los planes de cosecha");
        return ResponseEntity.ok(planCosechaService.listarPlanCosecha());
    }

    // @GetMapping puede dar un cuerpo a la URL, esto es vital para segregar las consultas
    // y evitar confusiones a nivel de lógica y persona
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Plan de cosecha por ID", description = "Permite obtener los datos de un plan de cosecha")
    @ApiResponse(responseCode = "200", description = "Plan de cosecha encontrado")
    @ApiResponse(responseCode = "404", description = "Plan de cosecha no encontrado")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        log.info("[msplanCosecha] GET /api/planCosecha/{} - Buscando plan de cosecha por ID", id);
        if(!planCosechaService.existePorid(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe, intente de nuevo");
        }
        return ResponseEntity.ok(planCosechaService.obtenerPorId(id));
    }

    // POST
    // @Valid nos permite asegurar que los atributos que contenga el @RequestBody no estén vacíos
    @PostMapping("/guardar/{idEspecie}")
    @Operation(summary = "Guardar Plan de cosecha",
            description = "Guarda un plan de cosecha nuevo en la base de datos, necesita el ID de una especie para guardarse")
    @ApiResponse(responseCode = "201", description = "Plan de cosecha registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    public ResponseEntity<?> guardarPlanCosecha(@PathVariable Long idEspecie,
                                                @Valid @RequestBody PlanCosecha planCosecha){
        log.info("[msplanCosecha] POST /api/planCosecha/guardar/{} - Guardando nuevo plan de cosecha", idEspecie);
        return ResponseEntity.ok(planCosechaService.guardarPlanCosecha(idEspecie, planCosecha));
    }

    // PUT
    // @Valid nos permite asegurar que los atributos que contenga el @RequestBody no estén vacíos
    @PutMapping("/actualizarCompleto/{id}/{idEspecie}")
    @Operation(summary = "Actualizar Plan de cosecha completo",
            description = "Este método permite cambiar completamente los datos de un plan de cosecha, incluyendo los datos de la especie")
    @ApiResponse(responseCode = "200", description = "Plan de cosecha actualizado")
    @ApiResponse(responseCode = "404", description = "Plan de cosecha no encontrado")
    public ResponseEntity<?> actualizarPlanCompleto(@PathVariable Long id,
                                                    @PathVariable Long idEspecie,
                                                    @Valid @RequestBody PlanCosecha planCosecha){
        log.info("[msplanCosecha] PUT /api/planCosecha/actualizarCompleto/{}/{} - Actualizando plan de cosecha completo", id, idEspecie);
        return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
    }

    // PATCH
    /*
        @Validated nos permite asegurar que los atributos que contenga el @RequestBody no estén vacíos.
        @Validated, a diferencia de @Valid, aplica la validación en cada atributo por separado, en vez
        de hacerlo para todo el objeto, esto permite que se dé como válido un cuerpo que no tenga todos
        los atributos
     */
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Plan de cosecha",
            description = "Este método permite cambiar parcialmente los datos de un plan de cosecha. Se actualizarán todos los datos de especie en caso de que se agregue una ID junto al del plan de cosecha")
    @ApiResponse(responseCode = "200", description = "Plan de cosecha actualizado parcialmente")
    @ApiResponse(responseCode = "404", description = "Plan de cosecha no encontrado")
    public ResponseEntity<?> actualizarParcial(
            @PathVariable Long id,
            @Validated @RequestBody PlanCosechaDTO dto) {
        log.info("[msplanCosecha] PATCH /api/planCosecha/actualizarParcial/{} - Actualizando plan de cosecha parcialmente", id);
        return planCosechaService.actualizarPlanCosecha(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar Plan de cosecha",
            description = "Este método permite eliminar completamente un plan de cosecha de la base de datos")
    @ApiResponse(responseCode = "200", description = "Plan de cosecha eliminado")
    @ApiResponse(responseCode = "404", description = "Plan de cosecha no encontrado")
    public ResponseEntity<?> eliminarPlanCosecha(Long id){
        log.info("[msplanCosecha] DELETE /api/planCosecha/eliminar - Eliminando plan de cosecha con ID: {}", id);
        if(!planCosechaService.existePorid(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
        }
        planCosechaService.eliminarPorId(id);
        return ResponseEntity.ok("Plan de Cosecha "+id+" eliminada");
    }
}
