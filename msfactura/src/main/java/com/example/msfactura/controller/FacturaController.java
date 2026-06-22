package com.example.msfactura.controller;
import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
import com.example.msfactura.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factura")
@Tag(name = "Controller / Factura",description = "Metodos para interactuar con el msfactura")
public class FacturaController{
    private final FacturaService facturaService;
    public FacturaController(FacturaService facturaService){
        this.facturaService=facturaService;
    }
    // GET
    @GetMapping
    @Operation(summary = "Listar Factura",
            description = "Obtiene todas las facturas")
    public ResponseEntity<?> listarFactura() {
            return ResponseEntity.ok(facturaService.listarFactura());
    }
    @GetMapping("{id}")
    @Operation(summary = "Buscar por ID",
            description = "Permite obtener los datos de un factura")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
            if (!facturaService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La factura con id "+ id +" no fue encontrada, intente de nuevo");
            }
            return ResponseEntity.ok(facturaService.buscarPorId(id));
    }
    // POST
    @PostMapping("/guardar/{idPredio}/{idCliente}")
    @Operation(summary = "Guardar Plan de cosecha",
    description = "Guarda una factura en la base de datos, " +
                "nesecita el ID de un predio y un cliente para guardarse")
    public ResponseEntity<?> guardarFactura(@PathVariable Long idPredio,
                                            @PathVariable Long idCliente,
                                            @Valid @RequestBody Factura factura){
            return ResponseEntity.ok(facturaService.guardarFactura(idPredio,idCliente,factura));
    }
    // PUT
    @PutMapping("/actualizar/{id}/{idPredio}/{idCliente}")
    @Operation(summary = "Actualizar Factura completa",
            description = "Este metodo permite cambiar completamente los datos de una factura, " +
                        "incluyendo los datos del predio y el cliente")
    public ResponseEntity<?> actualizarFacturaCompleta(@PathVariable Long id,
                                                       @PathVariable Long idPredio,
                                                       @PathVariable Long idCliente,
                                                       @Valid @RequestBody Factura factura){
            return ResponseEntity.ok(facturaService.actualizarFacturaCompleta(id,idPredio,idCliente,factura));
    }
    //PATCH
    @PatchMapping("/actualizarParcial/{id}")
    @Operation(summary = "Actualizar Factura",
            description = "Este metodo permite cambiar parcialmente los datos de una factura." +
                    "Se actualizaran todos los datos del predio y/o el cliente en caso de que se agregen las respectivas ID")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id,
                                               @Validated @RequestBody FacturaDTO facturaDTO){
        return ResponseEntity.ok(facturaService.actualizarFacturaParcial(id,facturaDTO));
    }
    // DELETE
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar Factura",
            description = "Este metodo permite eliminar completamente una factura de la base de datos")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id){
            if(!facturaService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            facturaService.eliminarFactura(id);
            return ResponseEntity.ok("Factura "+ id + " eliminada");
    }
}