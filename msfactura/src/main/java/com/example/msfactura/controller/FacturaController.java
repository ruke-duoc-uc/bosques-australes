package com.example.msfactura.controller;

import com.example.msfactura.model.Factura;
import com.example.msfactura.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @PostMapping("/agregar/{id}")
    public ResponseEntity<?> agregarFactura(@PathVariable Long id, @RequestBody Factura factura){
        try{
            Factura nueva = facturaService.crearFactura(id, factura.getDescripcion());
            return ResponseEntity.ok(nueva);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se logro agregar la factura, intente de nuevo");
        }
    }

}