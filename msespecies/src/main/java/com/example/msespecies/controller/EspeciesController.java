package com.example.msespecies.controller;

import com.example.msespecies.model.Especies;
import com.example.msespecies.model.EspeciesDTO;
import com.example.msespecies.service.EspeciesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/especies")
@Tag(name = "Controller / Especies",description = "Metodos para interactuar con el msespecies")
public class EspeciesController {

    private static final Logger log = LoggerFactory.getLogger(EspeciesController.class);
    private final EspeciesService especiesService;
    public EspeciesController(EspeciesService especiesService){
        this.especiesService = especiesService;
    }
    // GET
    @GetMapping
    @Operation(summary= "Listar Especies",
            description = "Este metodo permite ver todos los detalles de las especies que se trabajan")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<?> listarEspecies(){
        log.info("[msespecies] GET /api/especies - Listando todas las especies");
        return ResponseEntity.ok(especiesService.listarEspecies());

    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Especie por ID",
            description = "Este metodo se usa para obtener los datos de una sola especie, se utiliza en otros microservicios con el mismo fin")
    @ApiResponse(responseCode = "200", description = "Especie encontrada")
    @ApiResponse(responseCode = "404", description = "Especie no encontrada")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        log.info("[msespecies] GET /api/especies/{} - Buscando especie por ID", id);
        if(!especiesService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con el ID "+id);
        }
        return ResponseEntity.ok(especiesService.buscarPorId(id));
    }
    // POST
    //@Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PostMapping("/agregar")
    @Operation(summary = "Guardar Especie",
            description = "Este metodo permite agregar una especie de árbol que no existiera antes en el microservicio")
    @ApiResponse(responseCode = "201", description = "Especie registrada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    public ResponseEntity<?> guardarEspecie(@Valid @RequestBody Especies especies) {
        log.info("[msespecies] POST /api/especies/agregar - Guardando nueva especie");
        return ResponseEntity.ok(especiesService.guardarEspecie(especies));
    }
    // PUT
    //@Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar Especie completa",
            description = "Este metodo permite actualizar todos los datos de una especie de árbol. " +
                    "Este metodo se asegura que no intentes actualizar una especie inexistente")
    @ApiResponse(responseCode = "200", description = "Especie actualizada")
    @ApiResponse(responseCode = "404", description = "Especie no encontrada")
    public ResponseEntity<?> actualizarEspecie(@PathVariable Long id,@Valid @RequestBody Especies especies){
        log.info("[msespecies] PUT /api/especies/actualizar/{} - Actualizando especie completa", id);
        if(!especiesService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con el ID "+id);
        }
        return ResponseEntity.ok(especiesService.actualizarEspecie(id, especies));
    }
    // PATCH
    // @Validated, a diferencia de @Valid, evalua los atributos otorgados,
    // no exige la existencia de todos los atributos
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Especie",
            description = "Este metodo permite actualizar parcialmente los datos de una especie")
    @ApiResponse(responseCode = "200", description = "Especie actualizada parcialmente")
    @ApiResponse(responseCode = "404", description = "Especie no encontrada")
    public ResponseEntity<?> actualizarParcialEspecie(@PathVariable Long id, @Validated @RequestBody EspeciesDTO dto){
        log.info("[msespecies] PATCH /api/especies/actualizarParcial/{} - Actualizando especie parcialmente", id);
        return especiesService.actualizarParcialEspecie(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // DELETE
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar Especie",
            description = "Este metodo permite eliminar permanentemente una especie, ya no podra ser referenciada en otros microservicios. " +
                    "Este metodo se asegura que no intentes actualizar una especie inexistente")
    @ApiResponse(responseCode = "200", description = "Especie eliminada")
    @ApiResponse(responseCode = "404", description = "Especie no encontrada")
    public ResponseEntity<?> eliminarEspecie(@PathVariable Long id){
        log.info("[msespecies] DELETE /api/especies/eliminar/{} - Eliminando especie", id);
        if(!especiesService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una especie con la id "+ id);
        }
        especiesService.eliminarEspecie(id);
        return ResponseEntity.ok("Especie "+id+ " eliminada");
    }
}
