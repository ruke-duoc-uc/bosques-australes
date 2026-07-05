package com.example.mstrabajadores.repository;

import com.example.mstrabajadores.model.TrabajadoresModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad TrabajadoresModel.
 * Además de los métodos CRUD heredados de JpaRepository (findAll, findById,
 * save, delete, etc.), define un método de consulta personalizado:
 * existsByRut.
 */
@Repository
public interface TrabajadoresRepository extends JpaRepository<TrabajadoresModel, Long>{

    /**
     * Método de consulta derivado (query method): Spring Data JPA genera
     * automáticamente la implementación a partir del nombre del método.
     * Al llamarse "existsByRut", Spring entiende que debe verificar si existe
     * algún registro en la tabla "trabajadores" cuyo campo "rut" coincida
     * con el valor recibido. Se traduce internamente a algo como:
     *   SELECT COUNT(*) > 0 FROM trabajadores WHERE rut = ?
     * Se usa en TrabajadoresService.save() para evitar registrar
     * dos trabajadores con el mismo RUT.
     */
    boolean existsByRut(String rut);
}