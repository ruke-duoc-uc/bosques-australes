package com.example.msacopio.controller;

import com.example.msacopio.model.AcopioModel;
import com.example.msacopio.service.AcopioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acopios")
public class AcopioController {
    private final AcopioService acopioService;

    public AcopioController(AcopioService acopioService){
        this.acopioService = acopioService;
    }

    @GetMapping
    public ResponseEntity<List<AcopioModel>> listarTodos() {
        return ResponseEntity.ok(acopioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcopioModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(acopioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AcopioModel> crear(@RequestBody AcopioModel acopio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(acopioService.crear(acopio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcopioModel> actualizar(@PathVariable Long id,
                                                  @RequestBody AcopioModel datosNuevos) {
        return ResponseEntity.ok(acopioService.actualizar(id, datosNuevos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        acopioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
