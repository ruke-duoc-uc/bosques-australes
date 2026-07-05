package com.example.seguridad.repository;
import com.example.seguridad.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CAPA DE PERSISTENCIA (REPOSITORIO DATA JPA) - HISTÓRICO DE ACCIDENTES
 * Interfaz encargada de las transacciones SQL directas sobre la tabla "accidentes".
 * Expone operaciones CRUD automáticas heredadas de JpaRepository y define consultas derivadas
 * por convención de nombres (Query Methods) para auditorías operativas específicas en terreno.
 */
public interface SeguridadRepository extends JpaRepository<Accidente, Long>{
    /**
     * ¿Qué hace?: Filtra e identifica todos los incidentes asociados a un operario en particular.
     * Uso: Útil para evaluar la tasa de accidentabilidad individual de un trabajador.
     */
    List<Accidente> findByTrabajadorId(Long trabajadorId);
    /**
     * ¿Qué hace?: Recupera los siniestros ocurridos dentro de una cuadrilla específica.
     * Uso: Clave para inspecciones de seguridad en zonas forestales con alto índice de riesgo.
     */
    List<Accidente> findByCuadrillaId(Long cuadrillaId);
    /**
     * ¿Qué hace?: Agrupa los accidentes según su nivel de impacto (LEVE, GRAVE, FATAL).
     * Uso: Utilizado para la generación de reportes e indicadores de gestión (KPIs) de la empresa.
     */
    List<Accidente> findByGravedad(GravedadAccidente gravedad);
}
