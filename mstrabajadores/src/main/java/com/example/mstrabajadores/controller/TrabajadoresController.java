package com.example.mstrabajadores.controller;

import com.example.mstrabajadores.model.TrabajadoresModel;
import com.example.mstrabajadores.service.TrabajadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

/**
 * Controlador REST del microservicio de Trabajadores.
 * Expone los endpoints CRUD, documentados con anotaciones de Swagger
 * (@Tag, @Operation, @ApiResponse) para que aparezcan en Swagger UI.
 */
@RestController
@RequestMapping("/api/trabajadores")
@Tag(
        name = "Trabajadores",
        description = "Operaciones relacionadas con la gestión de trabajadores"
)
public class TrabajadoresController {

    //Inyección por campo (@Autowired). Funciona, aunque la inyección por
    //constructor (como en DespachoController/AcopioController) es considerada
    //mejor práctica: facilita testear con mocks y deja las dependencias
    //explícitas e inmutables (permite usar "final").
    @Autowired
    private TrabajadoresService service;

    //GET /api/trabajadores
    @Operation(
            summary = "Listar trabajadores",
            description = "Obtiene todos los trabajadores registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa"
    )
    @GetMapping
    public List<TrabajadoresModel> getAll() {
        return service.getAll();
    }

    //GET /api/trabajadores/{id}
    @Operation(
            summary = "Buscar trabajador por ID",
            description = "Obtiene un trabajador específico según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador encontrado"),
            @ApiResponse(responseCode = "404", description = "Trabajador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TrabajadoresModel> getById(
            @Parameter(description = "Identificador único del trabajador", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    //POST /api/trabajadores
    //Nota: a diferencia de otros controllers, este endpoint retorna directamente
    //el TrabajadoresModel (sin envolver en ResponseEntity), por lo que Spring
    //responde igual con 200 OK, pero sin la posibilidad de personalizar el status.
    @Operation(
            summary = "Registrar trabajador",
            description = "Crea un nuevo trabajador en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador creado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public TrabajadoresModel save(@RequestBody TrabajadoresModel trabajador) {
        return service.save(trabajador);
    }

    //PUT /api/trabajadores/{id}
    @Operation(
            summary = "Actualizar trabajador",
            description = "Actualiza los datos de un trabajador existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Trabajador no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TrabajadoresModel> update(
            @Parameter(description = "Identificador único del trabajador", example = "1")
            @PathVariable Long id,
            @RequestBody TrabajadoresModel trabajador) {
        return ResponseEntity.ok(service.actualizar(id, trabajador));
    }

    //DELETE /api/trabajadores/{id}
    @Operation(
            summary = "Eliminar trabajador",
            description = "Elimina un trabajador según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Trabajador eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Trabajador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador único del trabajador", example = "1")
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}