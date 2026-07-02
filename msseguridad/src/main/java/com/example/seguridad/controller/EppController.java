package com.example.seguridad.controller;

import com.example.seguridad.dto.EppRequestDto;
import com.example.seguridad.model.Epp;
import com.example.seguridad.service.EppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/epps")
@Tag(name = "Controlador EPP", description = "Endpoints para la gestión y asignación de Equipos de Protección Personal")
public class EppController {

    private final EppService eppService;

    public EppController(EppService eppService) {
        this.eppService = eppService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las entregas de EPP", description = "Obtiene el listado general de los equipos entregados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Epp>> listarTodos() {
        return ResponseEntity.ok(eppService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener EPP por ID")
    @ApiResponse(responseCode = "200", description = "EPP encontrado")
    @ApiResponse(responseCode = "404", description = "EPP no encontrado")
    public ResponseEntity<Epp> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eppService.obtenerPorId(id));
    }

    @GetMapping("/trabajador/{trabajadorId}")
    @Operation(summary = "Listar EPP por Trabajador", description = "Filtra los equipos asignados a un operario en particular")
    public ResponseEntity<List<Epp>> listarPorTrabajador(@PathVariable Long trabajadorId) {
        return ResponseEntity.ok(eppService.listarPorTrabajador(trabajadorId));
    }

    @GetMapping("/trabajador/{trabajadorId}/vigente")
    @Operation(summary = "Verificar vigencia de EPP", description = "Comprueba en tiempo real si el operario cuenta con equipamiento activo y seguro")
    public ResponseEntity<Map<String, Object>> verificarVigencia(@PathVariable Long trabajadorId) {
        boolean tieneActivo = eppService.trabajadorTieneEppActivo(trabajadorId);
        return ResponseEntity.ok(Map.of(
                "trabajadorId", trabajadorId,
                "eppVigente", tieneActivo,
                "mensaje", tieneActivo
                        ? "El trabajador cuenta con EPP activos registrados."
                        : "ALERTA: El trabajador no tiene EPP activos o registrados."
        ));
    }

    @PostMapping
    @Operation(summary = "Registrar entrega de EPP", description = "Guarda una nueva entrega asignando fechas de vencimiento")
    @ApiResponse(responseCode = "201", description = "Entrega registrada")
    public ResponseEntity<Epp> registrar(@Valid @RequestBody EppRequestDto dto) {
        Epp epp = new Epp();
        epp.setTrabajadorId(dto.getTrabajadorId());
        epp.setTipo(dto.getTipo());
        epp.setFechaEntrega(dto.getFechaEntrega());
        epp.setFechaVencimiento(dto.getFechaVencimiento());
        epp.setObservaciones(dto.getObservaciones());

        return ResponseEntity.status(HttpStatus.CREATED).body(eppService.registrar(epp));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar EPP existente")
    public ResponseEntity<Epp> actualizar(@PathVariable Long id, @Valid @RequestBody EppRequestDto dto) {
        Epp eppActualizado = new Epp();
        eppActualizado.setTipo(dto.getTipo());
        eppActualizado.setFechaEntrega(dto.getFechaEntrega());
        eppActualizado.setFechaVencimiento(dto.getFechaVencimiento());
        eppActualizado.setObservaciones(dto.getObservaciones());

        return ResponseEntity.ok(eppService.actualizar(id, eppActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar EPP (Baja lógica)", description = "Cambia el estado de vigencia del equipamiento a falso")
    @ApiResponse(responseCode = "24", description = "EPP dado de baja con éxito")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        eppService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}