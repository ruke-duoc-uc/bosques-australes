package com.example.msplanCosecha.service;

import com.example.msplanCosecha.client.EspeciesClient;
import com.example.msplanCosecha.client.EspeciesDTO;
import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.repository.PlanCosechaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanCosechaService {
    private final PlanCosechaRepository planCosechaRepository;
    private final EspeciesClient especiesClient;
    public PlanCosechaService(PlanCosechaRepository planCosechaRepository, EspeciesClient especiesClient){
        this.planCosechaRepository = planCosechaRepository;
        this.especiesClient = especiesClient;
    }
    //Get
    public List<PlanCosecha> obtenerTodos(){
        return planCosechaRepository.findAll();
    }
    public PlanCosecha obtenerPorId(Long id){
        return planCosechaRepository.findById(id).orElse(null);
    }

    //Existe (manejo de error 404)
    public Boolean existePorid(Long id){
        return planCosechaRepository.existsById(id);
    }

    //Post
    public PlanCosecha crearPlanCosecha(Long idEspecie, Long edad, Double alturaP){
        EspeciesDTO especiesDTO = especiesClient.obtenerDatosCliente(idEspecie);
        PlanCosecha planCosechaN = new PlanCosecha();
        //Atributos factura
        planCosechaN.setAlturaPromedio(alturaP);
        planCosechaN.setEdadRodal(edad);
        //Atributos Especies
        planCosechaN.setEspecie(especiesDTO.nombre());
        return planCosechaRepository.save(planCosechaN);
    }

    //Put
    public Optional<PlanCosecha> actualizarPlanCompleto(Long id,Long idEspecie, PlanCosecha planActualizado){
        //Para cambiar la especie debemos consultar con el msespecie, por ello agregamos
        //EspeciesDTO para actualizar un planCosecha completo
        EspeciesDTO especiesDTO = especiesClient.obtenerDatosCliente(idEspecie);
        return planCosechaRepository.findById(id).map(planCosecha -> {
            planCosecha.setAlturaPromedio(planActualizado.getAlturaPromedio());
            planCosecha.setEdadRodal(planActualizado.getEdadRodal());
            planCosecha.setEspecie(especiesDTO.nombre());
            return planCosechaRepository.save(planCosecha);
        });
    }

    //Delete
    public void eliminarPorId(Long id){
        planCosechaRepository.deleteById(id);
    }
}
