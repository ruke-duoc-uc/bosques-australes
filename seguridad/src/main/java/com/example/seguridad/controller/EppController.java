package com.example.seguridad.controller;

import com.example.seguridad.dto.EppRequestDto; // Asegúrate que el nombre coincida
import com.example.seguridad.model.Epp;
import com.example.seguridad.service.EppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/epps")
public class EppController {

    private final EppService eppService;

    public EppController(EppService eppService) {
        this.eppService = eppService;
    }

    @GetMapping
    public ResponseEntity<List<Epp>> listarTodos() {
        return ResponseEntity.ok(eppService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Epp> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eppService.obtenerPorId(id));
    }

    @GetMapping("/trabajador/{trabajadorId}")
    public ResponseEntity<List<Epp>> listarPorTrabajador(@PathVariable Long trabajadorId) {
        return ResponseEntity.ok(eppService.listarPorTrabajador(trabajadorId));
    }

    /**
     * Verifica si el trabajador tiene algún EPP activo registrado.
     */
    @GetMapping("/trabajador/{trabajadorId}/vigente")
    public ResponseEntity<Map<String, Object>> verificarVigencia(@PathVariable Long trabajadorId) {
        // Cambiado para coincidir con el método del Service simplificado
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
    public ResponseEntity<Epp> registrar(@Valid @RequestBody EppRequestDto dto) {
        // Mapeo manual del DTO a la Entidad
        Epp epp = new Epp();
        epp.setTrabajadorId(dto.getTrabajadorId());
        epp.setTipo(dto.getTipo());
        epp.setFechaEntrega(dto.getFechaEntrega()); // String
        epp.setFechaVencimiento(dto.getFechaVencimiento()); // String
        epp.setObservaciones(dto.getObservaciones());

        return ResponseEntity.status(HttpStatus.CREATED).body(eppService.registrar(epp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Epp> actualizar(@PathVariable Long id, @Valid @RequestBody EppRequestDto dto) {
        // Mapeo manual para la actualización
        Epp eppActualizado = new Epp();
        eppActualizado.setTipo(dto.getTipo());
        eppActualizado.setFechaEntrega(dto.getFechaEntrega());
        eppActualizado.setFechaVencimiento(dto.getFechaVencimiento());
        eppActualizado.setObservaciones(dto.getObservaciones());

        return ResponseEntity.ok(eppService.actualizar(id, eppActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        eppService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}