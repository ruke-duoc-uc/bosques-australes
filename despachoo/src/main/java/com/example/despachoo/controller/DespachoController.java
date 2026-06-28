package com.example.despachoo.controller;

import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.service.DespachoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController

@RequestMapping("/api/despachos")
@Tag(
        name = "Despachos",
        description = "Operaciones relacionadas con el despacho de productos forestales"
)
public class DespachoController {

    private final DespachoService despachoService;

    public DespachoController(DespachoService despachoService) {
        this.despachoService = despachoService;
    }

    @Operation(
            summary = "Listar despachos",
            description = "Obtiene todos los despachos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa"
    )
    @GetMapping
    public List<DespachoModel> listarTodos() {
        return despachoService.listarTodos();
    }

    @Operation(
            summary = "Buscar despacho por ID",
            description = "Obtiene un despacho específico según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despacho encontrado"),
            @ApiResponse(responseCode = "404", description = "Despacho no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(despachoService.buscarPorId(id));
    }

    @Operation(
            summary = "Registrar despacho",
            description = "Crea un nuevo despacho asociado a una especie y una factura"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despacho creado correctamente"),
            @ApiResponse(responseCode = "404", description = "Especie o factura no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/{idEspecies}/{idFactura}")
    public ResponseEntity<?> guardar(
            @PathVariable Long idEspecies,
            @PathVariable Long idFactura,
            @RequestBody DespachoModel despacho) {

        return ResponseEntity.ok(
                despachoService.guardar(despacho, idEspecies, idFactura));
    }

    @Operation(
            summary = "Actualizar despacho",
            description = "Actualiza los datos de un despacho existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despacho actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Despacho, especie o factura no encontrada")
    })
    @PutMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                     @PathVariable Long idEspecies,
                                     @PathVariable Long idFactura,
                                     @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(despachoService.actualizar(id, idEspecies, idFactura, despacho));
    }

    @Operation(
            summary = "Eliminar despacho",
            description = "Elimina un despacho según su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despacho eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Despacho no encontrado")
    })
    @DeleteMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        despachoService.eliminarDespacho(id);
    return ResponseEntity.ok("Eliminado correctamente el despacho " + id);
    }
}
