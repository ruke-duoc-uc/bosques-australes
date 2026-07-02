package com.example.seguridad.repository;
import com.example.seguridad.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EppRepository extends JpaRepository<Epp, Long> {
    List<Epp> findByTrabajadorId(Long trabajadorId);
    List<Epp> findByTrabajadorIdAndActivoTrue(Long trabajadorId);
}
