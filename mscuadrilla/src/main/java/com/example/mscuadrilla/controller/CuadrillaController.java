package com.example.mscuadrilla.controller;

import com.example.mscuadrilla.dto.CuadrillaRequestDto;
import com.example.mscuadrilla.dto.CuadrillaResponseDto;
import com.example.mscuadrilla.model.Cuadrilla;
import com.example.mscuadrilla.service.CuadrillaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cuadrillas")
public class CuadrillaController {
    private final CuadrillaService service;

    public CuadrillaController(CuadrillaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CuadrillaResponseDto>> getAll() {
        List<CuadrillaResponseDto> dtos = service.listarTodas().stream()
                .map(this::convertirAValidResponseDto)
                .toList();
        if (dtos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> getDetalle(@PathVariable Long id) {
        // Al delegar la excepción 404 al Service, asumimos que si llega aquí siempre existe
        return ResponseEntity.ok(service.obtenerDetalleCuadrilla(id));
    }

    @PostMapping
    public ResponseEntity<CuadrillaResponseDto> create(@Valid @RequestBody CuadrillaRequestDto dto) {
        Cuadrilla cuadrilla = new Cuadrilla();
        mapearDtoAEntidad(dto, cuadrilla);

        Cuadrilla guardada = service.guardar(cuadrilla);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAValidResponseDto(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuadrillaResponseDto> update(@PathVariable Long id, @Valid @RequestBody CuadrillaRequestDto dto) {
        Cuadrilla datosNuevos = new Cuadrilla();
        mapearDtoAEntidad(dto, datosNuevos);

        Cuadrilla actualizada = service.actualizar(id, datosNuevos);
        return ResponseEntity.ok(convertirAValidResponseDto(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
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
