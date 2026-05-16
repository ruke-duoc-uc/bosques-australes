package com.example.msfactura.controller;

import com.example.msfactura.model.Factura;
import com.example.msfactura.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/factura")
public class FacturaController{

    private final FacturaService facturaService;
    public FacturaController(FacturaService facturaService){
        this.facturaService=facturaService;
    }

    @GetMapping
    public ResponseEntity<?> listarFactura() {
        try{
            return ResponseEntity.ok(facturaService.listarFactura());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se logro listar las facturas, intente denuevo");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        try{
            if (facturaService.buscarPorId(id) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La factura con id "+ id +" no fue encontrada, intente de nuevo");
            }
            return ResponseEntity.ok(facturaService.buscarPorId(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se logro obtener la factura, intente denuevo");
        }
    }
    @PostMapping("/agregar/{idPredio}/{idCliente}")
    public ResponseEntity<?> agregarFactura(@PathVariable Long idPredio,
                                            @PathVariable Long idCliente,
                                            @RequestBody Factura factura){
            Factura nueva = facturaService.crearFactura(
                    idPredio,
                    idCliente,
                    factura.getNumFactura(),
                    factura.getGiro(),
                    factura.getMonto());
            return ResponseEntity.ok(nueva);
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarFacturaCompleta(@PathVariable Long id, @RequestBody Factura factura){
        try{
            return facturaService.actualizarFactura(id, factura)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la factura");
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id){
        try{
            if(!facturaService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La id "+id+" no existe");
            }
            return ResponseEntity.ok("Factura "+ id + " eliminada");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar la factura");
        }
    }
}