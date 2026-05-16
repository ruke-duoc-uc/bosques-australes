package com.example.mstrabajadores.controller;

import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.service.TrabajadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<TrabajadoresModel> getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    public TrabajadoresModel save(@RequestBody TrabajadoresModel trabajador){
        return service.save(trabajador);
    }

    @PutMapping("/{id}")
    public TrabajadoresModel update(@PathVariable Long id, @RequestBody TrabajadoresModel trabajador){
        trabajador.setId(id);
        return service.save(trabajador);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "Trabajador eliminado correctamente";
    }
}
