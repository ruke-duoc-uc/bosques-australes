package com.example.msplanCosecha.controller;

import com.example.msplanCosecha.model.PlanCosechaDTO;
import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.service.PlanCosechaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planCosecha")
@Tag(name = "Plan Cosecha",description = "Microservicio encargado de los planes de cosecha")
public class PlanCosechaController {
    private final PlanCosechaService planCosechaService;
    public PlanCosechaController(PlanCosechaService planCosechaService){
        this.planCosechaService = planCosechaService;
    }
    @GetMapping
    @Operation(summary = "Listar Planes de cosecha",
    description = "Este metodo obtiene todos los datos de los planes de cosecha")
    public ResponseEntity<?> listarPlanCosecha(){
            return ResponseEntity.ok(planCosechaService.listarPlanCosecha());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Plan de cosecha por ID",
    description = "Este metodo permite obtener los datos de un plan de cosecha")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
            if(!planCosechaService.existePorid(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            return ResponseEntity.ok(planCosechaService.obtenerPorId(id));
    }
    @PostMapping("/guardar/{idEspecie}")
    @Operation(summary = "Guardar Plan de cosecha",
    description = "Este metodo guarda un plan de cosecha nuevo en la base de datos, nesecita el ID de una especie para guardarse")
    public ResponseEntity<?> guardarPlanCosecha(@PathVariable Long idEspecie, @RequestBody PlanCosecha planCosecha){
            return ResponseEntity.ok(planCosechaService.guardarPlanCosecha(idEspecie, planCosecha));
    }
    // Patch
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Plan de cosecha",
    description = "Este metodo permite cambiar parcialmente los datos de un plan de cosecha." +
            "Se actualizaran todos los datos  de especie en caso de que se agrege una ID junto al del plan de cosecha")
    public ResponseEntity<?> actualizarParcial(
            @PathVariable Long id,
            @RequestBody PlanCosechaDTO dto) {
        return planCosechaService.actualizarPlanCosecha(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actualizarCompleto/{id}/{idEspecie}")
    @Operation(summary = "Actualizar Plan de cosecha completo",
    description = "Este metodo permite cambiar completamente los datos de un plan de cosecha, incluyendo los datos de la especie")
    public ResponseEntity<?> actualizarPlanCompleto(@PathVariable Long id,@PathVariable Long idEspecie,@RequestBody PlanCosecha planCosecha){
            return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
    }
    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar Plan de cosecha",
    description = "Este metodo permite eliminar completamente un plan de cosecha de la base de datos")
    public ResponseEntity<?> eliminarPlanCosecha(Long id){
            if(!planCosechaService.existePorid(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            planCosechaService.eliminarPorId(id);
            return ResponseEntity.ok("Plan de Cosecha "+id+" eliminada");
    }
}