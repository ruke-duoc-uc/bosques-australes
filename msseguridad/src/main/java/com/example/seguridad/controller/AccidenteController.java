package com.example.seguridad.controller;

import com.example.seguridad.dto.AccidenteRequestDto;
import com.example.seguridad.model.Accidente;
import com.example.seguridad.service.AccidenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accidentes")
@Tag(name = "Controlador Accidentes", description = "Endpoints para el registro y gestión de siniestros laborales")
public class AccidenteController {

    private static final Logger log = LoggerFactory.getLogger(AccidenteController.class);
    private final AccidenteService accidenteService;

    public AccidenteController(AccidenteService accidenteService) {
        this.accidenteService = accidenteService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los accidentes", description = "Obtiene un listado histórico de siniestros registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Accidente>> listarTodos() {
        return ResponseEntity.ok(accidenteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener accidente por ID", description = "Busca el detalle de un accidente específico mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Accidente encontrado")
    @ApiResponse(responseCode = "404", description = "Accidente no encontrado")
    public ResponseEntity<Accidente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(accidenteService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un accidente", description = "Crea un nuevo reporte de accidente interceptando y validando los campos")
    @ApiResponse(responseCode = "201", description = "Accidente registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    public ResponseEntity<Accidente> registrar(@Valid @RequestBody AccidenteRequestDto dto) {
        log.info("[msseguridad] POST /accidentes - Registrando siniestro");

        Accidente accidente = new Accidente();
        accidente.setTrabajadorId(dto.getTrabajadorId());
        accidente.setCuadrillaId(dto.getCuadrillaId());
        accidente.setFechaHoraOcurrencia(dto.getFechaHoraOcurrencia());
        accidente.setDescripcion(dto.getDescripcion());
        accidente.setTipo(dto.getTipo());
        accidente.setGravedad(dto.getGravedad());

        return ResponseEntity.status(HttpStatus.CREATED).body(accidenteService.registrar(accidente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un accidente", description = "Permite modificar la descripción, tipo o gravedad de un siniestro existente")
    @ApiResponse(responseCode = "200", description = "Accidente actualizado")
    @ApiResponse(responseCode = "404", description = "Accidente no encontrado")
    public ResponseEntity<Accidente> actualizar(@PathVariable Long id, @RequestBody AccidenteRequestDto dto) {
        log.info("[msseguridad] PUT /accidentes/{} - Actualizando datos", id);

        Accidente existente = accidenteService.obtenerPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setDescripcion(dto.getDescripcion());
        existente.setTipo(dto.getTipo());
        existente.setGravedad(dto.getGravedad());

        return ResponseEntity.ok(accidenteService.registrar(existente));
    }
}