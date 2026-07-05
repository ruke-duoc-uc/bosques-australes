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

/**
 * Controlador REST del microservicio de Despacho.
 * Expone los endpoints HTTP que permiten a otros sistemas (u otros microservicios)
 * interactuar con los despachos: listar, buscar, crear, actualizar y eliminar.
 * Todas las rutas de esta clase comienzan con "/api/despachos".
 */

@RestController //Combina @Controller + @ResponseBody: cada método retorna directamente datos (JSON), no vistas.
@RequestMapping("/api/despachos") // Prefijo base para todas las rutas definidas en esta clase.
public class DespachoController {

    //Se inyecta el service que contiene la lógica de negocio real.
    //El controller no hace lógica, solo recibe la petición HTTP y delega el trabajo.
    private final DespachoService despachoService;

    //Inyección de dependencias por constructor (buena práctica, permite testear más fácil con mocks).
    public DespachoController(DespachoService despachoService) {
        this.despachoService = despachoService;
    }

    //GET /api/despachos
    // Retorna la lista completa de despachos registrados.
    @GetMapping
    public List<DespachoModel> listarTodos() {
        return this.despachoService.listarTodos();
    }

    // --- GET /api/despachos/{id} ---
    // Busca un despacho específico según su id.
    @GetMapping({"/{id}"})
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.despachoService.buscarPorId(id));
    }

    //POST /api/despachos/{idEspecies}/{idFactura}
    //Crea un nuevo despacho. Recibe además el id de la especie y el id de la factura
    //asociadas, ya que estos datos se validan/completan consultando otros microservicios.
    @PostMapping({"/{idEspecies}/{idFactura}"})
    public ResponseEntity<?> guardar(@PathVariable Long idEspecies, @PathVariable Long idFactura, @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(this.despachoService.guardar(despacho, idEspecies, idFactura));
    }

    //PUT /api/despachos/{id}/{idEspecies}/{idFactura}
    //Actualiza un despacho existente, refrescando también los datos de especie y factura
    //consultados a los microservicios externos.
    @PutMapping({"/{id}/{idEspecies}/{idFactura}"})
    public ResponseEntity<?> actualizar(@PathVariable Long id, @PathVariable Long idEspecies, @PathVariable Long idFactura, @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(this.despachoService.actualizar(id, idEspecies, idFactura, despacho));
    }

    //DELETE /api/despachos/{id}/{idEspecies}/{idFactura}
    //Elimina un despacho según su id.
    //Nota: idEspecies e idFactura no se usan dentro del método, solo id.
    @DeleteMapping({"/{id}/{idEspecies}/{idFactura}"})
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        this.despachoService.eliminarDespacho(id);
        return ResponseEntity.ok("Eliminado correctamente el despacho " + id);
    }
}