package com.example.mspredios.controller;
import com.example.mspredios.model.Predios;
import com.example.mspredios.service.PrediosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/predios")
public class PrediosController {
    private final PrediosService prediosService;
    public PrediosController(PrediosService prediosService) {
        this.prediosService = prediosService;
    }
    @GetMapping
    public List<Predios> listarPredios(){
        return prediosService.listarPredios();
    }

    @GetMapping("/{id}")
    public Predios buscarPorId(@PathVariable Long id){
        return prediosService.buscarPorId(id);
    }

    @PostMapping
    public Predios guardarPredio(Predios predios){
        return prediosService.guardarPredio(predios);
    }
}
