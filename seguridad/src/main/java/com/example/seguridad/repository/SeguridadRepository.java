package com.example.seguridad.repository;
import com.example.seguridad.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguridadRepository extends JpaRepository<Accidente, Long>{
    List<Accidente> findByFaenaId(Long faenaId);
    List<Accidente> findByTrabajadorId(Long trabajadorId);
    List<Accidente> findByCuadrillaId(Long cuadrillaId);
    List<Accidente> findByGravedad(GravedadAccidente gravedad);

    boolean existsByFaenaIdAndFaenaBloqueadaTrue(Long faenaId);
    List<Accidente> findByFaenaIdAndFaenaBloqueadaTrue(Long faenaId);
}
