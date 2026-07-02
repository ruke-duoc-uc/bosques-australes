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

@RestController
@RequestMapping("/api/planCosecha")
@Tag(name = "Controller / Plan Cosecha",description = "Metodos para interactuar con el msplanCosecha")
public class PlanCosechaController {
    private static final Logger log = LoggerFactory.getLogger(PlanCosechaController.class);
    private final PlanCosechaService planCosechaService;
    public PlanCosechaController(PlanCosechaService planCosechaService){
        this.planCosechaService = planCosechaService;
    }
    // GET
    @GetMapping
    @Operation(summary = "Listar Planes de cosecha",
            description = "Obtiene todos los planes de cosecha")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<?> listarPlanCosecha(){
        log.info("[msplanCosecha] GET /api/planCosecha - Listando todos los planes de cosecha");
        return ResponseEntity.ok(planCosechaService.listarPlanCosecha());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Plan de cosecha por ID",
            description = "Permite obtener los datos de un plan de cosecha")
    @ApiResponse(responseCode = "200", description = "Plan de cosecha encontrado")
    @ApiResponse(responseCode = "404", description = "Plan de cosecha no encontrado")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        log.info("[msplanCosecha] GET /api/planCosecha/{} - Buscando plan de cosecha por ID", id);
        if(!planCosechaService.existePorid(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
        }
        return ResponseEntity.ok(planCosechaService.obtenerPorId(id));
    }
    // POST
    @PostMapping("/guardar/{idEspecie}")
    @Operation(summary = "Guardar Plan de cosecha",
            description = "Guarda un plan de cosecha nuevo en la base de datos, " +
                    "nesecita el ID de una especie para guardarse")
    @ApiResponse(responseCode = "201", description = "Plan de cosecha registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    public ResponseEntity<?> guardarPlanCosecha(@PathVariable Long idEspecie,
                                                @Valid @RequestBody PlanCosecha planCosecha){
        log.info("[msplanCosecha] POST /api/planCosecha/guardar/{} - Guardando nuevo plan de cosecha", idEspecie);
        return ResponseEntity.ok(planCosechaService.guardarPlanCosecha(idEspecie, planCosecha));
    }
    // PUT
    @PutMapping("/actualizarCompleto/{id}/{idEspecie}")
    @Operation(summary = "Actualizar Plan de cosecha completo",
            description = "Este metodo permite cambiar completamente los datos de un plan de cosecha, incluyendo los datos de la especie")
    @ApiResponse(responseCode = "200", description = "Plan de cosecha actualizado")
    @ApiResponse(responseCode = "404", description = "Plan de cosecha no encontrado")
    public ResponseEntity<?> actualizarPlanCompleto(@PathVariable Long id,
                                                    @PathVariable Long idEspecie,
                                                    @Valid @RequestBody PlanCosecha planCosecha){
        log.info("[msplanCosecha] PUT /api/planCosecha/actualizarCompleto/{}/{} - Actualizando plan de cosecha completo", id, idEspecie);
        return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
    }
    // PATCH
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Plan de cosecha",
            description = "Este metodo permite cambiar parcialmente los datos de un plan de cosecha." +
                    "Se actualizaran todos los datos  de especie en caso de que se agrege una ID junto al del plan de cosecha")
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
            description = "Este metodo permite eliminar completely un plan de cosecha de la base de datos")
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
