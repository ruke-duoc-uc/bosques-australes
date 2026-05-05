package com.example.mspredios.controller;
import com.example.mspredios.model.Predios;
import com.example.mspredios.service.PrediosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/predios")
public class PrediosController {
    private final PrediosService prediosService;
    public PrediosController(PrediosService prediosService) {
        this.prediosService = prediosService;
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
        return ResponseEntity.ok(prediosService.buscarPorId(id));
    }catch (Exception e){
         return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Ocurrio un error al buscar el predio");
     }
    }

    @PostMapping
    public ResponseEntity<?> guardarPredio(Predios predios){
        try {
            return ResponseEntity.ok(prediosService.guardarPredio(predios));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Ocurrio un error al buscar el predio");
        }
        }
}
