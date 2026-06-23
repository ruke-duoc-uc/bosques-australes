package com.example.mstrabajadores.controller;

import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.service.TrabajadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrajadoresController {

    @Autowired
    private TrabajadoresService service;

    @GetMapping
    public List<TrabajadoresModel> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadoresModel> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public TrabajadoresModel save(@RequestBody TrabajadoresModel trabajador){
        return service.save(trabajador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadoresModel> update(@PathVariable Long id, @RequestBody TrabajadoresModel trabajador){
        return ResponseEntity.ok(service.actualizar(id, trabajador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
