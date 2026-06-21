package com.example.msfactura.controller;
import com.example.msfactura.model.Factura;
import com.example.msfactura.model.FacturaDTO;
import com.example.msfactura.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factura")
public class FacturaController{
    private final FacturaService facturaService;
    public FacturaController(FacturaService facturaService){
        this.facturaService=facturaService;
    }
    // GET
    @GetMapping
    public ResponseEntity<?> listarFactura() {
            return ResponseEntity.ok(facturaService.listarFactura());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
            if (!facturaService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La factura con id "+ id +" no fue encontrada, intente de nuevo");
            }
            return ResponseEntity.ok(facturaService.buscarPorId(id));
    }
    // POST
    @PostMapping("/guardar/{idPredio}/{idCliente}")
    public ResponseEntity<?> guardarFactura(@PathVariable Long idPredio,
                                            @PathVariable Long idCliente,
                                            @Valid @RequestBody Factura factura){
            return ResponseEntity.ok(facturaService.guardarFactura(idPredio,idCliente,factura));
    }
    // PUT
    @PutMapping("/actualizar/{id}/{idPredio}/{idCliente}")
    public ResponseEntity<?> actualizarFacturaCompleta(@PathVariable Long id,
                                                       @PathVariable Long idPredio,
                                                       @PathVariable Long idCliente,
                                                       @Valid @RequestBody Factura factura){
            return ResponseEntity.ok(facturaService.actualizarFacturaCompleta(id,idPredio,idCliente,factura));
    }
    //PATCH
    @PatchMapping("/actualizarParcial/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id,
                                               @Validated @RequestBody FacturaDTO facturaDTO){
        return ResponseEntity.ok(facturaService.actualizarFacturaParcial(id,facturaDTO));
    }
    // DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id){
            if(!facturaService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            facturaService.eliminarFactura(id);
            return ResponseEntity.ok("Factura "+ id + " eliminada");
    }
}