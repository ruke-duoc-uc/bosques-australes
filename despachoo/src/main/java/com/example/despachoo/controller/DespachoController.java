package com.example.despachoo.controller;

import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/despachos")
public class DespachoController {

    private final DespachoService despachoService;
    public DespachoController(DespachoService despachoService) {
            this.despachoService = despachoService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(despachoService.listarTodos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo listar los despachos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            if(!despachoService.existePorId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un despacho con el ID "+id);
            }
            return ResponseEntity.ok(despachoService.buscarPorId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo buscar el despacho");
        }
    }

    @PostMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> guardar(@RequestBody DespachoModel despacho) {
        try {
            return ResponseEntity.ok(despachoService.guardar(despacho));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo guardar la factura");
        }
        }

    @PutMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                     @PathVariable Long idEspecies,
                                     @PathVariable Long idFactura,
                                     @RequestBody DespachoModel despacho) {
        try {
        return ResponseEntity.ok(despachoService.actualizar(id, idEspecies, idFactura, despacho));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar la factura");
    }
    }
    @DeleteMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
        despachoService.eliminarDespacho(id);
    return ResponseEntity.ok("Eliminado correctamente el despacho " + id);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar la factura");
    }
    }
}
