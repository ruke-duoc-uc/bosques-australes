package com.example.msplanCosecha.controller;

import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.service.PlanCosechaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planCosecha")
public class PlanCosechaController {
    private final PlanCosechaService planCosechaService;
    public PlanCosechaController(PlanCosechaService planCosechaService){
        this.planCosechaService = planCosechaService;
    }
    @GetMapping
    public ResponseEntity<?> listarPlanCosecha(){
            return ResponseEntity.ok(planCosechaService.listarPlanCosecha());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
            if(!planCosechaService.existePorid(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            return ResponseEntity.ok(planCosechaService.obtenerPorId(id));
    }
    @PostMapping("/guardar/{idEspecie}")
    public ResponseEntity<?> guardarPlanCosecha(@PathVariable Long idEspecie, @RequestBody PlanCosecha planCosecha){
            return ResponseEntity.ok(planCosechaService.guardarPlanCosecha(idEspecie, planCosecha));
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPlanCosecha(@PathVariable Long id,@PathVariable Long idEspecie,@RequestBody PlanCosecha planCosecha){
            return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
    }
    @PutMapping("/actualizarCompleto/{id}/{idEspecie}")
    public ResponseEntity<?> actualizarPlanCompleto(@PathVariable Long id,@PathVariable Long idEspecie,@RequestBody PlanCosecha planCosecha){
            return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
    }
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPlanCosecha(Long id){
            if(!planCosechaService.existePorid(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            planCosechaService.eliminarPorId(id);
            return ResponseEntity.ok("Plan de Cosecha "+id+" eliminada");
    }
}
