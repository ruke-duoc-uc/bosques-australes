package com.example.mspredios.controller;

import com.example.mspredios.model.Predios;
import com.example.mspredios.model.PrediosDTO;
import com.example.mspredios.service.PrediosService;
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

// Avisa a Spring que esta clase es el controller
@RestController
// Da el cuerpo base para usar los metodos de este microservicio
@RequestMapping("/api/predios")
// Da un nombre al conjunto de metodos presentes en este controller
@Tag(name = "Controller / Predios", description = "Metodos para interactuar con el mspredios")
public class PrediosController {
    private static final Logger log = LoggerFactory.getLogger(PrediosController.class);
    private final PrediosService prediosService;

    public PrediosController(PrediosService prediosService) {
        this.prediosService = prediosService;
    }

    // GET
    // @GetMapping permite que el metodo solo responda a una solicitud GET
    @GetMapping
    // @Operation describe el metodo en Swagger, dando un resumen y descripcion
    @Operation(summary = "Obtiene todas los predios",
            description = "Este metodo permite ver todos los predios, de Bosques Australes y externos")
    // Configura las respuestas de Swagger ante ciertos codigos
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<?> listarPredios() {
        log.info("[mspredios] GET /api/predios - Listando todos los predios");
        return ResponseEntity.ok(prediosService.listarPredios());
    }

    // @GetMapping puede dar un cuerpo a la URL, esto es vital para segregar las consultas
    // y evitar confusiones a nivel de logica y persona
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un predio especifico",
            description = "Este metodo permite obtener los datos de un predio especifico, se utiliza en otros microservicios")
    @ApiResponse(responseCode = "200", description = "Predio encontrado")
    @ApiResponse(responseCode = "404", description = "Predio no encontrado")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        log.info("[mspredios] GET /api/predios/{} - Buscando predio por ID", id);
        return ResponseEntity.ok(prediosService.buscarPorId(id));
    }

    // POST
    // @Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PostMapping("/guardar")
    @Operation(summary = "Crea un predio en el microservicio",
            description = "Este metodo permite agregar un predio que previamente no existia en el microservicio")
    @ApiResponse(responseCode = "201", description = "Predio registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    public ResponseEntity<?> guardarPredio(@Valid @RequestBody Predios predios) {
        log.info("[mspredios] POST /api/predios/guardar - Guardando nuevo predio");
        return ResponseEntity.ok(prediosService.guardarPredio(predios));
    }

    // PUT
    // @Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualiza los datos del microservicio",
            description = "Este metodo permite actualizar todos los datos de un predio ya creado. " +
                    "Este metodo se asegura que no intentes actualizar un predio inexistente")
    @ApiResponse(responseCode = "200", description = "Predio actualizado")
    @ApiResponse(responseCode = "404", description = "Predio no encontrado")
    public ResponseEntity<?> actualizarPredio(@PathVariable Long id, @Valid @RequestBody Predios predios) {
        log.info("[mspredios] PUT /api/predios/actualizar/{} - Actualizando predio completo", id);
        if (!prediosService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID " + id);
        }
        return ResponseEntity.ok(prediosService.actualizarPredio(id, predios));
    }

    // PATCH
    /*
        @Validated nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios.
        @Validated, a diferencia de @Valid, aplica la validacion en cada atributo por separado, en vez
        de hacerlo para todo el objeto, esto permite que se de como valido un cuerpo que no tenga todos
        los atributos
     */
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Especie",
            description = "Este metodo permite actualizar parcialmente los datos de una especie")
    @ApiResponse(responseCode = "200", description = "Predio actualizado parcialmente")
    @ApiResponse(responseCode = "404", description = "Predio no encontrado")
    public ResponseEntity<?> actualizarParcialEspecie(@PathVariable Long id, @Validated @RequestBody PrediosDTO dto) {
        log.info("[mspredios] PATCH /api/predios/actualizarParcial/{} - Actualizando predio parcialmente", id);
        return prediosService.actualizarParcialPredios(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    // Este metodo permite eliminar un predio de la base de datos del microservicio
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina los predios del microservicio",
            description = "Este metodo permite eliminar permanentemente los datos de un predio, no podra ser referenciado de nuevo por otros microservicios. " +
                    "Este metodo se aegura que no intentes eliminar un predio inexistente")
    @ApiResponse(responseCode = "200", description = "Predio eliminado")
    @ApiResponse(responseCode = "404", description = "Predio no encontrado")
    public ResponseEntity<?> eliminarPredio(@PathVariable Long id) {
        log.info("[mspredios] DELETE /api/predios/eliminar/{} - Eliminando predio", id);
        if (!prediosService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un predio con ID " + id);
        }
        prediosService.eliminarPredio(id);
        return ResponseEntity.ok("Predio " + id + " eliminado");
    }
}
