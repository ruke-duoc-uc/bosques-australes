package com.example.msacopio.controller;

import com.example.msacopio.model.AcopioModel;
import com.example.msacopio.service.AcopioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/guardar/{id}")
    public ResponseEntity<AcopioModel> crear(@PathVariable Long id, @RequestBody AcopioModel acopio) {
        return ResponseEntity.ok(acopioService.crear(acopio, id));
    }

    @PutMapping("/actualizar/{id}/{idEspecies}")
    public ResponseEntity<Optional<AcopioModel>> actualizar(@PathVariable Long id,
                                                            @RequestBody AcopioModel datosNuevos,
                                                            @PathVariable Long idEspecies) {
        return ResponseEntity.ok(acopioService.actualizar(id, idEspecies, datosNuevos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        acopioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
