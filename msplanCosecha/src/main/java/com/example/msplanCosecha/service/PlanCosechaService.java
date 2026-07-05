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

/*
    @Service deriva el manejo de esta clase
    a Spring, permitiendo su uso en el controller
 */
@Service
public class PlanCosechaService {
    private static final Logger log = LoggerFactory.getLogger(PlanCosechaService.class);
    private final PlanCosechaRepository planCosechaRepository;
    private final EspeciesClient especiesClient;
    public PlanCosechaService(PlanCosechaRepository planCosechaRepository, EspeciesClient especiesClient){
        this.planCosechaRepository = planCosechaRepository;
        this.especiesClient = especiesClient;
    }
    // GET
    // Este metodo obtiene todos los objetos almacenados en la base de datos del microservicio
    public List<PlanCosecha> listarPlanCosecha(){
        log.info("[msplanCosecha] Service - Listando todos los planes de cosecha desde el repositorio");
        return planCosechaRepository.findAll();
    }
    // Este metodo obtiene un objeto almacenado en la base de datos del microservicio
    // usando su ID para encontrarlo
    public PlanCosecha obtenerPorId(Long id){
        log.info("[msplanCosecha] Service - Buscando plan de cosecha por ID: {}", id);
        return planCosechaRepository.findById(id).orElse(null);
    }

    // Este metodo comprueba a nivel aplicacion que exista un plan de cosecha (manejo de error 404)
    public Boolean existePorid(Long id){
        log.info("[msplanCosecha] Service - Verificando existencia de plan de cosecha con ID: {}", id);
        return planCosechaRepository.existsById(id);
    }

    // POST
    // Este metodo permite crear un nuevo plan de cosecha
    // Nesecita un cuerpo con los datos propios de plan de cosecha, ademas de dar la ID de la especie
    // correspondiente para la consulta a traves de "client"
    public PlanCosecha guardarPlanCosecha(Long idEspecie,PlanCosecha planCosecha){
        log.info("[msplanCosecha] Service - Guardando nuevo plan de cosecha para la especie ID: {}", idEspecie);
        EspeciesDTO especiesDTO = especiesClient.obtenerDatosEspecie(idEspecie);
        PlanCosecha planCosechaN = new PlanCosecha();
        //Atributos plan de cosecha
        planCosechaN.setAlturaPromedio(planCosecha.getAlturaPromedio());
        planCosechaN.setEdadRodal(planCosecha.getEdadRodal());
        planCosechaN.setDescripcion(planCosecha.getDescripcion());
        //Atributos Especies
        planCosechaN.setEspecie(especiesDTO.nombre());
        return planCosechaRepository.save(planCosechaN);
    }
    // PUT
    // Permite una actualizacion completa de los datos en el plan de cosecha
    // Exige todos los atributos de plan de cosecha, ademas de el ID de la especie
    public Optional<PlanCosecha> actualizarPlanCompleto(Long id,Long idEspecie, PlanCosecha planActualizado){
        log.info("[msplanCosecha] Service - Actualizando plan de cosecha completo con ID: {} y especie ID: {}", id, idEspecie);
        //Para cambiar la especie debemos consultar con el msespecie, por ello agregamos
        //EspeciesDTO para actualizar un planCosecha completo
        EspeciesDTO especiesDTO = especiesClient.obtenerDatosEspecie(idEspecie);
        return planCosechaRepository.findById(id).map(planCosecha -> {
            planCosecha.setAlturaPromedio(planActualizado.getAlturaPromedio());
            planCosecha.setEdadRodal(planActualizado.getEdadRodal());
            planCosecha.setDescripcion(planActualizado.getDescripcion());
            planCosecha.setEspecie(especiesDTO.nombre());
            return planCosechaRepository.save(planCosecha);
        });
    }
    // PATCH
    // Permite la actualizacion parcial de los datos de un plan de cosecha
    /*
        Due al uso de PlanCosechaDTO, la ID se da en el cuerpo,
        esto permite que se pueda actualizar solo la especie, o solo los atributos propios
    */
    public Optional<PlanCosecha> actualizarPlanCosecha(Long id, PlanCosechaDTO dto) {
        log.info("[msplanCosecha] Service - Actualizando parcialmente plan de cosecha con ID: {}", id);
        return planCosechaRepository.findById(id).map(planCosecha -> {
            // Datos Plan de Cosecha
            if (dto.alturaPromedio() != null) {
                planCosecha.setAlturaPromedio(dto.alturaPromedio());
            }
            if (dto.edadRodal() != null) {
                planCosecha.setEdadRodal(dto.edadRodal());
            }
            if (dto.descripcion() != null) {
                planCosecha.setDescripcion(dto.descripcion());
            }
            // Si se otorga una ID en el cuerpo, se actualizaran todos los datos de la especie externa
            // Datos Especie
            if (dto.idEspecie() != null) {
                EspeciesDTO especiesDTO = especiesClient.obtenerDatosEspecie(dto.idEspecie());
                if (especiesDTO != null) {
                    planCosecha.setEspecie(especiesDTO.nombre());
                }
            }
            return planCosechaRepository.save(planCosecha);
        });}
    // DELETE
    // Este metodo permite eliminar un plan de cosecha de la base de datos del microservicio
    public void eliminarPorId(Long id){
        log.info("[msplanCosecha] Service - Eliminando plan de cosecha con ID: {}", id);
        planCosechaRepository.deleteById(id);
    }
}