package com.example.mspredios.controller;
import com.example.mspredios.model.Predios;
import com.example.mspredios.model.PrediosDTO;
import com.example.mspredios.service.PrediosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predios")
@Tag(name = "Predios", description = "Metodos del microservicio predios")
@Schema(description = "Manejo de Predios")
public class PrediosController{
    private final PrediosService prediosService;
    public PrediosController(PrediosService prediosService) {this.prediosService = prediosService;
    }
    //GET Global
    @GetMapping
    @Operation(summary="Obtiene todas los predios",
            description = "Este metodo permite ver todos los predios, de Bosques Australes y externos")
    public ResponseEntity<?> listarPredios(){
            return ResponseEntity.ok(prediosService.listarPredios());
    }
    //GET Individual
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un predio especifico",
    description = "Este metodo permite obtener los datos de un predio especifico, se utiliza en otros microservicios")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prediosService.buscarPorId(id));
    }
    //POST
    //@Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios    @PostMapping("/guardar")
    @Operation(summary = "Crea un predio en el microservicio",
    description = "Este metodo permite agregar un predio que previamente no existia en el microservicio")
    public ResponseEntity<?> guardarPredio(@Valid @RequestBody Predios predios){
            return ResponseEntity.ok(prediosService.guardarPredio(predios));
        }
    //PUT
    //@Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualiza los datos del microservicio",
    description = "Este metodo permite actualizar todos los datos de un predio ya creado. " +
    "Este metodo se asegura que no intentes actualizar un predio inexistente")
    public ResponseEntity<?> actualizarPredio(@PathVariable Long id, @Valid @RequestBody Predios predios){
            if (!prediosService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID "+id);
            }
            return ResponseEntity.ok(prediosService.actualizarPredio(id, predios));
    }
    //PATCH
    //@Validated nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    // @Validated, a diferencia de @Valid, evalua los atributos otorgados,
    // no exige la existencia de todos los atributos
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Especie",
            description = "Este metodo permite actualizar parcialmente los datos de una especie")
    public ResponseEntity<?> actualizarParcialEspecie(@PathVariable Long id,@Validated @RequestBody PrediosDTO dto){
        return prediosService.actualizarParcialEspecie(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina los predios del microservicio",
    description = "Este metodo permite eliminar permanentemente los datos de un predio, no podra ser referenciado de nuevo por otros microservicios. " +
            "Este metodo se aegura que no intentes eliminar un predio inexistente")
    public ResponseEntity<?> eliminarPredio(@PathVariable Long id){
            if (!prediosService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID "+id);
            }
            prediosService.eliminarPredio(id);
            return ResponseEntity.ok("Predio "+id+ " eliminado");
    }
}
