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
        try {
            return ResponseEntity.ok(planCosechaService.listarPlanCosecha());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo realizar la acción");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try{
            if(!planCosechaService.existePorid(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            return ResponseEntity.ok(planCosechaService.obtenerPorId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo realizar la busqueda por ID");
        }
    }
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarPlanCosecha(@PathVariable Long idEspecie, @RequestBody PlanCosecha planCosecha){
        try {
                    PlanCosecha planCosechaNueva = planCosechaService.guardarPlanCosecha(
                    idEspecie,
                    planCosecha.getEdadRodal(),
                    planCosecha.getAlturaPromedio());
            return ResponseEntity.ok(planCosechaNueva);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudeo agregar el plan de cosecha");
        }
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPlanCosecha(@PathVariable Long id,@PathVariable Long idEspecie,@RequestBody PlanCosecha planCosecha){
        try {
            return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el plan de cosecha "+ id);
        }
    }
    @PutMapping("/actualizarCompleto/{id}/{idEspecie}")
    public ResponseEntity<?> actualizarPlanCompleto(@PathVariable Long id,@PathVariable Long idEspecie,@RequestBody PlanCosecha planCosecha){
        try {
            return ResponseEntity.ok(planCosechaService.actualizarPlanCompleto(id,idEspecie,planCosecha));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el plan de cosecha "+ id);
        }
    }
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPlanCosecha(Long id){
        try {
            if(!planCosechaService.existePorid(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            planCosechaService.eliminarPorId(id);
            return ResponseEntity.ok("Plan de Cosecha "+id+" eliminada");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el plan de cosecha");
        }
    }
}
