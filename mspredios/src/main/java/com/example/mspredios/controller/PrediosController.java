package com.example.mspredios.controller;
import com.example.mspredios.model.Predios;
import com.example.mspredios.service.PrediosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predios")
public class PrediosController{
    private final PrediosService prediosService;
    public PrediosController(PrediosService prediosService) {this.prediosService = prediosService;
    }
    @GetMapping
    public ResponseEntity<?> listarPredios(){
        try {
            return ResponseEntity.ok(prediosService.listarPredios());
        }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo encontrar los predios");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
     try{
         if (!prediosService.existePorId(id)){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID "+id);
         }
        return ResponseEntity.ok(prediosService.buscarPorId(id));
    }catch (Exception e){
         return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Ocurrio un error al buscar el predio");
     }
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> guardarPredio(@RequestBody Predios predios){
        try {
            return ResponseEntity.ok(prediosService.guardarPredio(predios));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Ocurrio un error al buscar el predio");
        }
        }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPredio(@PathVariable Long id, @RequestBody Predios predios){
        try {
            if (!prediosService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID "+id);
            }
            return ResponseEntity.ok(prediosService.actualizarPredio(id, predios));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el predio");
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPredio(@PathVariable Long id){
        try {
            if (!prediosService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID "+id);
            }
            prediosService.eliminarPredio(id);
            return ResponseEntity.ok("Predio "+id+ " eliminado");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el predio");
        }
    }
}
