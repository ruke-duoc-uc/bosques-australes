package com.example.mscuadrilla.controller;

import com.example.mscuadrilla.dto.CuadrillaRequestDto;
import com.example.mscuadrilla.dto.CuadrillaResponseDto;
import com.example.mscuadrilla.model.Cuadrilla;
import com.example.mscuadrilla.service.CuadrillaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cuadrillas")
@Tag(name = "Controlador Cuadrillas", description = "Endpoints para la gestión operativa de cuadrillas forestales y asignaciones distribuidas")
public class CuadrillaController {
    private final CuadrillaService service;

    public CuadrillaController(CuadrillaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las cuadrillas", description = "Obtiene una lista con la información básica de todas las cuadrillas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuadrillas recuperada con éxito"),
            @ApiResponse(responseCode = "204", description = "No existen cuadrillas registradas en el sistema", content = @Content)
    })
    public ResponseEntity<List<CuadrillaResponseDto>> getAll() {
        List<CuadrillaResponseDto> dtos = service.listarTodas().stream()
                .map(this::convertirAValidResponseDto)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/detalle")
    @Operation(
            summary = "Obtener detalle distribuido de una cuadrilla",
            description = "Busca los datos de la cuadrilla local y consume vía RestClient el Microservicio de Trabajadores para armar el JSON completo con los datos de sus integrantes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de la cuadrilla y sus trabajadores procesado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID de la cuadrilla no existe en el sistema", content = @Content)
    })
    public ResponseEntity<Map<String, Object>> getDetalle(
            @Parameter(description = "ID de la cuadrilla a consultar", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerDetalleCuadrilla(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva cuadrilla", description = "Registra una cuadrilla en el sistema con su zona, especialidad y su lista inicial de IDs de trabajadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuadrilla creada de manera exitosa"),
            @ApiResponse(responseCode = "400", description = "Cuerpo de la petición inválido o faltan campos obligatorios", content = @Content)
    })
    public ResponseEntity<CuadrillaResponseDto> create(@Valid @RequestBody CuadrillaRequestDto dto) {
        Cuadrilla cuadrilla = new Cuadrilla();
        mapearDtoAEntidad(dto, cuadrilla);

        Cuadrilla guardada = service.guardar(cuadrilla);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAValidResponseDto(guardada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una cuadrilla existente", description = "Modifica los datos operativos de una cuadrilla o reasigna la lista de IDs de trabajadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuadrilla actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "La cuadrilla con el ID proporcionado no fue encontrada", content = @Content)
    })
    public ResponseEntity<CuadrillaResponseDto> update(
            @Parameter(description = "ID de la cuadrilla a actualizar", example = "1") @PathVariable Long id,
            @Valid @RequestBody CuadrillaRequestDto dto) {
        Cuadrilla datosNuevos = new Cuadrilla();
        mapearDtoAEntidad(dto, datosNuevos);

        Cuadrilla actualizar = service.actualizar(id, datosNuevos);
        return ResponseEntity.ok(convertirAValidResponseDto(actualizar));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cuadrilla", description = "Remueve físicamente el registro de la cuadrilla de la base de datos junto con su tabla de asignación de trabajadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "24", description = "Cuadrilla eliminada con éxito (No Content)"),
            @ApiResponse(responseCode = "404", description = "La cuadrilla seleccionada no existe", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la cuadrilla a eliminar", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --- MÉTODOS AUXILIARES DE TRASPASO ---
    private CuadrillaResponseDto convertirAValidResponseDto(Cuadrilla entidad) {
        CuadrillaResponseDto dto = new CuadrillaResponseDto();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setZona(entidad.getZona());
        dto.setEspecialidad(entidad.getEspecialidad());
        dto.setEstado(entidad.getEstado());
        // Inicializamos la lista vacía ya que el detalle completo se maneja en el endpoint /detalle
        dto.setTrabajadores(new ArrayList<>());
        return dto;
    }

    private void mapearDtoAEntidad(CuadrillaRequestDto dto, Cuadrilla entidad) {
        entidad.setNombre(dto.getNombre());
        entidad.setZona(dto.getZona());
        entidad.setEspecialidad(dto.getEspecialidad());
        entidad.setEstado(dto.getEstado());
        entidad.setTrabajadoresIds(dto.getTrabajadoresIds());
    }
}
