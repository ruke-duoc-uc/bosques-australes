package com.example.despachoo.controller;

import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.service.DespachoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/despachos"})
public class DespachoController {
    private final DespachoService despachoService;

    public DespachoController(DespachoService despachoService) {
        this.despachoService = despachoService;
    }

    @GetMapping
    public List<DespachoModel> listarTodos() {
        return this.despachoService.listarTodos();
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.despachoService.buscarPorId(id));
    }

    @PostMapping({"/{idEspecies}/{idFactura}"})
    public ResponseEntity<?> guardar(@PathVariable Long idEspecies, @PathVariable Long idFactura, @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(this.despachoService.guardar(despacho, idEspecies, idFactura));
    }

    @PutMapping({"/{id}/{idEspecies}/{idFactura}"})
    public ResponseEntity<?> actualizar(@PathVariable Long id, @PathVariable Long idEspecies, @PathVariable Long idFactura, @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(this.despachoService.actualizar(id, idEspecies, idFactura, despacho));
    }

    @DeleteMapping({"/{id}/{idEspecies}/{idFactura}"})
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        this.despachoService.eliminarDespacho(id);
        return ResponseEntity.ok("Eliminado correctamente el despacho " + id);
    }
}
