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

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
@Tag(
        name = "Trabajadores",
        description = "Operaciones relacionadas con la gestión de trabajadores"
)
public class TrabajadoresController {

    @Autowired
    private TrabajadoresService service;

    @Operation(
            summary = "Listar trabajadores",
            description = "Obtiene todos los trabajadores registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa"
    )
    @GetMapping
    public List<TrabajadoresModel> getAll(){
        return service.getAll();
    }

    @Operation(
            summary = "Buscar trabajador por ID",
            description = "Obtiene un trabajador específico según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador encontrado"),
            @ApiResponse(responseCode = "404", description = "Trabajador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TrabajadoresModel> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(
            summary = "Registrar trabajador",
            description = "Crea un nuevo trabajador en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador creado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public TrabajadoresModel save(@RequestBody TrabajadoresModel trabajador){
        return service.save(trabajador);
    }

    @Operation(
            summary = "Actualizar trabajador",
            description = "Actualiza los datos de un trabajador existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trabajador actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Trabajador no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TrabajadoresModel> update(@PathVariable Long id, @RequestBody TrabajadoresModel trabajador){
        return ResponseEntity.ok(service.actualizar(id, trabajador));
    }

    @Operation(
            summary = "Eliminar trabajador",
            description = "Elimina un trabajador según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Trabajador eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Trabajador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
