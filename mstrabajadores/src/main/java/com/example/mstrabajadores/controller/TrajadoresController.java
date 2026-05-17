package com.example.mstrabajadores.controller;

import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.service.TrabajadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trabajadores")
public class TrajadoresController {
    //Esto reemplaza la declaracion "final" de java
    @Autowired
    private TrabajadoresService service;

    @GetMapping
    public ResponseEntity<?> listarTrabajadores(){
        try{
            return ResponseEntity.ok(service.listarTrabajadores());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo listar a los trabajadores");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorid(@PathVariable Long id){
        try {
            if(!service.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El trabajador con ID "+id+" no existe");
            }
            return ResponseEntity.ok(service.buscarPorId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo buscar al trabajador");
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> guardarTrabajador(@RequestBody TrabajadoresModel trabajador) {
        try{
        return ResponseEntity.ok(service.guardarTrabajador(trabajador));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo guardar al trabajador");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTrabajador(@PathVariable Long id, @RequestBody TrabajadoresModel trabajador) {
       try{
        if (!service.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El trabajador con ID " + id + " no existe");
        }
            return ResponseEntity.ok(service.actualizarTrabajador(id, trabajador));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar los datos del trabajador");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTrabajador(@PathVariable Long id){
        try{
            if(!service.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El trabajador con ID "+id+" no existe");
            }
            service.eliminarTrabajador(id);
            return ResponseEntity.ok("Se elimino al trabajador con ID" + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el trabajador");
        }
    }
}
