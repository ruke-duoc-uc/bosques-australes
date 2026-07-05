package com.example.msfactura.controller;

import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
import com.example.msfactura.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// Avisa a Spring que esta clase es el controller
@RestController
// Da el cuerpo base para usar los metodos de este microservicio
@RequestMapping("/api/factura")
// Da un nombre al conjunto de metodos presentes en este controller
@Tag(name = "Controller / Factura", description = "Metodos para interactuar con el msfactura")
public class FacturaController {
    private static final Logger log = LoggerFactory.getLogger(FacturaController.class);
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    // GET
    // @GetMapping permite que el metodo solo responda a una solicitud GET
    @GetMapping
    // @Operation Describe el metodo en Swagger, dando un resumen y descripcion
    @Operation(summary = "Listar Factura", description = "Obtiene todas las facturas")
    // Configura las respuestas de Swagger ante ciertos codigos
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<?> listarFactura() {
        log.info("[msfactura] GET /api/factura - Listando todas las facturas");
        return ResponseEntity.ok(facturaService.listarFactura());
    }

    // @GetMapping puede dar un cuerpo a la URL, esto es vital para segregar las consultas
    // y evitar confusiones a nivel de logica y persona
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Permite obtener los datos de una factura")
    @ApiResponse(responseCode = "200", description = "Factura encontrada")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        log.info("[msfactura] GET /api/factura/{} - Buscando factura por ID", id);
        if (!facturaService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La factura con id " + id + " no fue encontrada, intente de nuevo");
        }
        return ResponseEntity.ok(facturaService.buscarPorId(id));
    }

    // POST
    // @Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PostMapping("/guardar/{idPredio}/{idCliente}")
    @Operation(summary = "Guardar Plan de cosecha",
            description = "Guarda una factura en la base de datos, necesita el ID de un predio y un cliente para guardarse")
    @ApiResponse(responseCode = "201", description = "Factura registrada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    public ResponseEntity<?> guardarFactura(@PathVariable Long idPredio,
                                            @PathVariable Long idCliente,
                                            @Valid @RequestBody Factura factura) {
        log.info("[msfactura] POST /api/factura/guardar/{}/{} - Guardando nueva factura", idPredio, idCliente);
        return ResponseEntity.ok(facturaService.guardarFactura(idPredio, idCliente, factura));
    }

    // PUT
    // @Valid nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios
    @PutMapping("/actualizar/{id}/{idPredio}/{idCliente}")
    @Operation(summary = "Actualizar Factura completa",
            description = "Este metodo permite cambiar completamente los datos de una factura, incluyendo los datos del predio y el cliente")
    @ApiResponse(responseCode = "200", description = "Factura actualizada")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    public ResponseEntity<?> actualizarFacturaCompleta(@PathVariable Long id,
                                                       @PathVariable Long idPredio,
                                                       @PathVariable Long idCliente,
                                                       @Valid @RequestBody Factura factura) {
        log.info("[msfactura] PUT /api/factura/actualizar/{}/{}/{} - Actualizando factura completa", id, idPredio, idCliente);
        return ResponseEntity.ok(facturaService.actualizarFacturaCompleta(id, idPredio, idCliente, factura));
    }

    // PATCH
    /*
        @Validated nos permite asegurar que los atributos que contenga el @RequestBody no esten vacios.
        @Validated, a diferencia de @Valid, aplica la validacion en cada atributo por separado, en vez
        de hacerlo para todo el objeto, esto permite que se de como valido un cuerpo que no tenga todos
        los atributos
     */
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Factura",
            description = "Este metodo permite cambiar parcialmente los datos de una factura. Se actualizaran todos los datos del predio y/o el cliente en caso de que se agreguen las respectivas ID")
    @ApiResponse(responseCode = "200", description = "Factura actualizada parcialmente")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id,
                                               @Validated @RequestBody FacturaDTO facturaDTO) {
        log.info("[msfactura] PATCH /api/factura/actualizarParcial/{} - Actualizando factura parcialmente", id);
        return ResponseEntity.ok(facturaService.actualizarFacturaParcial(id, facturaDTO));
    }

    // DELETE
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar Factura", description = "Este metodo permite eliminar completamente una factura de la base de datos")
    @ApiResponse(responseCode = "200", description = "Factura eliminada")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id) {
        log.info("[msfactura] DELETE /api/factura/eliminar/{} - Eliminando factura", id);
        if (!facturaService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id " + id + " no existe");
        }
        facturaService.eliminarFactura(id);
        return ResponseEntity.ok("Factura " + id + " eliminada");
    }
}
