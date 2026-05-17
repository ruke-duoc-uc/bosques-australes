package com.example.cuadrilla.controller;

import com.example.cuadrilla.model.*;
import com.example.cuadrilla.service.*;
import com.example.cuadrilla.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/cuadrillas")
public class CuadrillaController {
    private final CuadrillaService service;

    public CuadrillaController(CuadrillaService service) {
        this.service = service;
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> getDetalle(@PathVariable Long id) {
        Map<String, Object> detalle = service.obtenerDetalleCuadrilla(id);
        return detalle != null ? ResponseEntity.ok(detalle) : ResponseEntity.notFound().build();
    }

    // Agrega estos a tu CuadrillaController

    @GetMapping
    public List<Cuadrilla> getAll() {
        return service.listarTodas(); // Debes crear este método en el service
    }

    @PostMapping
    public ResponseEntity<Cuadrilla> create(@RequestBody Cuadrilla cuadrilla) {
        return ResponseEntity.status(201).body(service.guardar(cuadrilla));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuadrilla> update(@PathVariable Long id, @RequestBody Cuadrilla cuadrilla) {
        return ResponseEntity.ok(service.actualizar(id, cuadrilla));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}