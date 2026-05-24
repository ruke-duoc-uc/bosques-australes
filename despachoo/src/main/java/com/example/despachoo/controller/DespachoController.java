package com.example.despachoo.controller;

import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<DespachoModel> listarTodos() {
        return despachoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(despachoService.buscarPorId(id));
    }

    @PostMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> guardar(@RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(despachoService.guardar(despacho));
    }

    @PutMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                     @PathVariable Long idEspecies,
                                     @PathVariable Long idFactura,
                                     @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(despachoService.actualizar(id, idEspecies, idFactura, despacho));
    }

    @DeleteMapping("/{id}/{idEspecies}/{idFactura}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        despachoService.eliminarDespacho(id);
    return ResponseEntity.ok("Eliminado correctamente el despacho " + id);
    }
}
