package com.example.msespecies.controller;

import com.example.msespecies.model.Especies;
import com.example.msespecies.service.EspeciesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/especies")
public class EspeciesController {

    private final EspeciesService especiesService;
    public EspeciesController(EspeciesService especiesService){
        this.especiesService = especiesService;
    }
    @GetMapping
    public ResponseEntity<?> listarEspecies(){
          return ResponseEntity.ok(especiesService.listarEspecies());

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            if(!especiesService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con el ID "+id);
            }
            return ResponseEntity.ok(especiesService.buscarPorId(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la especie");
        }
    }
    @PostMapping("/agregar")
    public ResponseEntity<?> guardarEspecie(@RequestBody Especies especies) {
        try {
            return ResponseEntity.ok(especiesService.guardarEspecie(especies));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar especie");
        }
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEspecie(@PathVariable Long id,@RequestBody Especies especies){
        if(!especiesService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con el ID "+id);
        }
        return ResponseEntity.ok(especiesService.actualizarEspecie(id, especies));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEspecie(@PathVariable Long id){
        try{
            if(!especiesService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con la id "+ id);
            }
            especiesService.eliminarEspecie(id);
            return ResponseEntity.ok("Especie "+id+ " eliminada");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al elimininar especie");
        }
    }
}
