package com.example.msespecies.controller;

import com.example.msespecies.model.Especies;
import com.example.msespecies.service.EspeciesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/especies")
@Tag(name = "Especies", description = "Metodos del microservicio especies")
@Schema(description = "Entidad que maneja las especies")
public class EspeciesController {

    private final EspeciesService especiesService;
    public EspeciesController(EspeciesService especiesService){
        this.especiesService = especiesService;
    }

    @GetMapping
    @Operation(summary="Obtiene todas las especies",
    description = "Este metodo permite ver todos los detalles de las especies que se trabajan")
    public ResponseEntity<?> listarEspecies(){
          return ResponseEntity.ok(especiesService.listarEspecies());

    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una especie",
    description = "Este metodo se usa para obtener los datos de una sola especie, se utiliza en otros microservicios con el mismo fin")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
            if(!especiesService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con el ID "+id);
            }
            return ResponseEntity.ok(especiesService.buscarPorId(id));
    }
    @PostMapping("/agregar")
    @Operation(summary = "Agrega una especie",
    description = "Este metodo permite agregar una especie de árbol que no existiera antes en el microservicio")
    public ResponseEntity<?> guardarEspecie(@RequestBody Especies especies) {
        return ResponseEntity.ok(especiesService.guardarEspecie(especies));
    }
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualiza una especie",
    description = "Este metodo permite actualizar todos los datos de una especie de árbol. " +
            "Este metodo se asegura que no intentes actualizar una especie inexistente")
    public ResponseEntity<?> actualizarEspecie(@PathVariable Long id,@RequestBody Especies especies){
        if(!especiesService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con el ID "+id);
        }
        return ResponseEntity.ok(especiesService.actualizarEspecie(id, especies));
    }
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina una especie",
    description = "Este metodo permite eliminar permanentemente una especie, ya no podra ser referenciada en otros microservicios. " +
            "Este metodo se asegura que no intentes actualizar una especie inexistente")
    public ResponseEntity<?> eliminarEspecie(@PathVariable Long id){
            if(!especiesService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con la id "+ id);
            }
            especiesService.eliminarEspecie(id);
            return ResponseEntity.ok("Especie "+id+ " eliminada");
    }
}
