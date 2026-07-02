package com.example.msacopio.controller;

import com.example.msacopio.model.AcopioModel;
import com.example.msacopio.service.AcopioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/acopios")
@Tag(
        name = "Acopios",
        description = "Operaciones relacionadas con la gestión de acopio de productos"
)
public class AcopioController {
    private final AcopioService acopioService;

    public AcopioController(AcopioService acopioService){

        this.acopioService = acopioService;
    }

    @Operation(
            summary = "Listar acopios",
            description = "Obtiene todos los acopios registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa"
    )
    @GetMapping
    public ResponseEntity<List<AcopioModel>> listarTodos() {
        return ResponseEntity.ok(acopioService.listarTodos());
    }

    @Operation(
            summary = "Buscar acopio por ID",
            description = "Obtiene un acopio específico según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acopio encontrado"),
            @ApiResponse(responseCode = "404", description = "Acopio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AcopioModel> buscarPorId(
            @Parameter(description = "Identificador único del acopio", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(acopioService.buscarPorId(id));
    }

    @Operation(
            summary = "Registrar acopio",
            description = "Crea un nuevo acopio en el sistema, asociándolo a una especie existente en el microservicio de Especies"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acopio creado correctamente"),
            @ApiResponse(responseCode = "404", description = "La especie indicada no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/guardar/{id}")
    public ResponseEntity<AcopioModel> crear(
            @Parameter(description = "Identificador de la especie asociada (microservicio de Especies)", example = "3")
            @PathVariable Long id,
            @RequestBody AcopioModel acopio) {
        return ResponseEntity.ok(acopioService.crear(acopio, id));
    }

    @Operation(
            summary = "Actualizar acopio",
            description = "Actualiza los datos de un acopio existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acopio actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Acopio o especie no encontrados")
    })
    @PutMapping("/actualizar/{id}/{idEspecies}")
    public ResponseEntity<AcopioModel> actualizar(
            @Parameter(description = "Identificador único del acopio", example = "1")
            @PathVariable Long id,
            @RequestBody AcopioModel datosNuevos,
            @Parameter(description = "Identificador de la especie asociada (microservicio de Especies)", example = "3")
            @PathVariable Long idEspecies) {
        return ResponseEntity.ok(acopioService.actualizar(id, idEspecies, datosNuevos));
    }

    @Operation(
            summary = "Eliminar acopio",
            description = "Elimina un acopio según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Acopio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Acopio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador único del acopio", example = "1")
            @PathVariable Long id) {
        acopioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
