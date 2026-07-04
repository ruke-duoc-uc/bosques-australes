package com.example.seguridad.repository;
import com.example.seguridad.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 * CAPA DE PERSISTENCIA (REPOSITORIO DATA JPA) - CONTROL DE INVENTARIO EPP
 * Interfaz encargada de la persistencia de datos para la asignación de Equipos de Protección Personal.
 * Provee los mecanismos de consulta para monitorizar la dotación e indumentaria activa de los equipos de campo.
 */
public interface EppRepository extends JpaRepository<Epp, Long> {
    /**
     * ¿Qué hace?: Obtiene el historial completo de todos los EPP (activos e inactivos) entregados a un operario.
     */
    List<Epp> findByTrabajadorId(Long trabajadorId);
    /**
     * ¿Qué hace?: Filtra únicamente la indumentaria de seguridad que se encuentra vigente (activo = true).
     * Uso: Es la consulta base para verificar si un trabajador cuenta con sus implementos al día en faena.
     */
    List<Epp> findByTrabajadorIdAndActivoTrue(Long trabajadorId);
}
