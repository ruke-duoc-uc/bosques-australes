package com.example.msplanCosecha.service;

import com.example.msplanCosecha.client.EspeciesClient;
import com.example.msplanCosecha.client.EspeciesDTO;
import com.example.msplanCosecha.model.PlanCosechaDTO;
import com.example.msplanCosecha.model.PlanCosecha;
import com.example.msplanCosecha.repository.PlanCosechaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanCosechaService {
    private static final Logger log = LoggerFactory.getLogger(PlanCosechaService.class);
    private final PlanCosechaRepository planCosechaRepository;
    private final EspeciesClient especiesClient;
    public PlanCosechaService(PlanCosechaRepository planCosechaRepository, EspeciesClient especiesClient){
        this.planCosechaRepository = planCosechaRepository;
        this.especiesClient = especiesClient;
    }
    //Get
    public List<PlanCosecha> listarPlanCosecha(){
        log.info("[msplanCosecha] Service - Listando todos los planes de cosecha desde el repositorio");
        return planCosechaRepository.findAll();
    }
    public PlanCosecha obtenerPorId(Long id){
        log.info("[msplanCosecha] Service - Buscando plan de cosecha por ID: {}", id);
        return planCosechaRepository.findById(id).orElse(null);
    }

    //Existe (manejo de error 404)
    public Boolean existePorid(Long id){
        log.info("[msplanCosecha] Service - Verificando existencia de plan de cosecha con ID: {}", id);
        return planCosechaRepository.existsById(id);
    }

    //Post
    public PlanCosecha guardarPlanCosecha(Long idEspecie,PlanCosecha planCosecha){
        log.info("[msplanCosecha] Service - Guardando nuevo plan de cosecha para la especie ID: {}", idEspecie);
        EspeciesDTO especiesDTO = especiesClient.obtenerDatosCliente(idEspecie);
        PlanCosecha planCosechaN = new PlanCosecha();
        //Atributos factura
        planCosechaN.setAlturaPromedio(planCosecha.getAlturaPromedio());
        planCosechaN.setEdadRodal(planCosecha.getEdadRodal());
        planCosechaN.setDescripcion(planCosecha.getDescripcion());
        //Atributos Especies
        planCosechaN.setEspecie(especiesDTO.nombre());
        return planCosechaRepository.save(planCosechaN);
    }
    //Put
    public Optional<PlanCosecha> actualizarPlanCompleto(Long id,Long idEspecie, PlanCosecha planActualizado){
        log.info("[msplanCosecha] Service - Actualizando plan de cosecha completo con ID: {} y especie ID: {}", id, idEspecie);
        //Para cambiar la especie debemos consultar con el msespecie, por ello agregamos
        //EspeciesDTO para actualizar un planCosecha completo
        EspeciesDTO especiesDTO = especiesClient.obtenerDatosCliente(idEspecie);
        return planCosechaRepository.findById(id).map(planCosecha -> {
            planCosecha.setAlturaPromedio(planActualizado.getAlturaPromedio());
            planCosecha.setEdadRodal(planActualizado.getEdadRodal());
            planCosecha.setDescripcion(planActualizado.getDescripcion());
            planCosecha.setEspecie(especiesDTO.nombre());
            return planCosechaRepository.save(planCosecha);
        });
    }
    // Patch
    public Optional<PlanCosecha> actualizarPlanCosecha(Long id, PlanCosechaDTO dto) {
        log.info("[msplanCosecha] Service - Actualizando parcialmente plan de cosecha con ID: {}", id);
        return planCosechaRepository.findById(id).map(planCosecha -> {
            if (dto.alturaPromedio() != null) {
                planCosecha.setAlturaPromedio(dto.alturaPromedio());
            }
            if (dto.edadRodal() != null) {
                planCosecha.setEdadRodal(dto.edadRodal());
            }
            if (dto.descripcion() != null) {
                planCosecha.setDescripcion(dto.descripcion());
            }
            if (dto.idEspecie() != null) {
                EspeciesDTO especiesDTO = especiesClient.obtenerDatosCliente(dto.idEspecie());
                if (especiesDTO != null) {
                    planCosecha.setEspecie(especiesDTO.nombre());
                }
            }
            return planCosechaRepository.save(planCosecha);
        });}
    //Delete
    public void eliminarPorId(Long id){
        log.info("[msplanCosecha] Service - Eliminando plan de cosecha con ID: {}", id);
        planCosechaRepository.deleteById(id);
    }
}
