package com.example.despachoo.controller;

import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/despachos")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @GetMapping
    public List<DespachoModel> listarTodos() {
        return despachoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<DespachoModel> buscarPorId(@PathVariable Long id) {
        return despachoService.buscarPorId(id);
    }

    @PostMapping
    public DespachoModel guardar(@RequestBody DespachoModel despacho) {
        return despachoService.guardar(despacho);
    }

    @PutMapping("/{id}")
    public DespachoModel actualizar(@PathVariable Long id, @RequestBody DespachoModel despacho) {
        return despachoService.actualizar(id, despacho);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        despachoService.eliminar(id);
    }
}
