package com.example.seguridad.controller;

import com.example.seguridad.dto.AccidenteRequestDto;
import com.example.seguridad.model.Accidente;
import com.example.seguridad.service.AccidenteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accidentes")
public class AccidenteController {

    private static final Logger log = LoggerFactory.getLogger(AccidenteController.class);
    private final AccidenteService accidenteService;

    public AccidenteController(AccidenteService accidenteService) {
        this.accidenteService = accidenteService;
    }

    @GetMapping
    public ResponseEntity<List<Accidente>> listarTodos() {
        return ResponseEntity.ok(accidenteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accidente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(accidenteService.obtenerPorId(id));
    }

    // POST → Registro de accidente (Usa el Service con lógica de Strings)
    @PostMapping
    public ResponseEntity<Accidente> registrar(@Valid @RequestBody AccidenteRequestDto dto) {
        log.info("[seguridad] POST /accidentes - Registrando siniestro");

        // Mapeo manual del DTO a la Entidad (Muy importante ya que el Service recibe la Entidad)
        Accidente accidente = new Accidente();
        accidente.setTrabajadorId(dto.getTrabajadorId());
        accidente.setCuadrillaId(dto.getCuadrillaId());
        accidente.setFechaHoraOcurrencia(dto.getFechaHoraOcurrencia()); // Tu String
        accidente.setDescripcion(dto.getDescripcion());
        accidente.setTipo(dto.getTipo());
        accidente.setGravedad(dto.getGravedad());

        return ResponseEntity.status(HttpStatus.CREATED).body(accidenteService.registrar(accidente));
    }
    // PUT → Actualizar datos básicos del accidente
    @PutMapping("/{id}")
    public ResponseEntity<Accidente> actualizar(@PathVariable Long id, @RequestBody AccidenteRequestDto dto) {
        log.info("[seguridad] PUT /accidentes/{} - Actualizando datos", id);

        Accidente existente = accidenteService.obtenerPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        // Actualizamos solo lo permitido
        existente.setDescripcion(dto.getDescripcion());
        existente.setTipo(dto.getTipo());
        existente.setGravedad(dto.getGravedad());

        // Guardamos usando el service
        return ResponseEntity.ok(accidenteService.registrar(existente));
    }
}