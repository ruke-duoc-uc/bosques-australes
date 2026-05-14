package com.example.msplanCosecha.service;

import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.repository.PlanCosechaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanCosechaService {
    private final PlanCosechaRepository planCosechaRepository;
    public PlanCosechaService(PlanCosechaRepository planCosechaRepository){
        this.planCosechaRepository = planCosechaRepository;
    }
    public List<PlanCosecha> obtenerTodos(){
        return planCosechaRepository.findAll();
    }
    public void existePorid(Long id){
        planCosechaRepository.existsById(id);
    }
    public PlanCosecha obtenerPorId(Long id){
        return planCosechaRepository.findById(id).orElse(null);
    }
    public void eliminarPorId(Long id){
        planCosechaRepository.deleteById(id);
    }
    public
}
