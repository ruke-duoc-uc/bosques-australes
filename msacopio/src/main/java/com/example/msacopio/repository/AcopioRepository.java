package com.example.msacopio.repository;

import com.example.msacopio.model.AcopioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para la entidad AcopioModel.
 * Al extender de JpaRepository, Spring Data JPA genera automáticamente
 * los métodos CRUD básicos (findAll, findById, save, delete, existsById, etc.).
 */
@Repository //Marca explícitamente el componente como repositorio (aunque JpaRepository ya lo detecta solo, es buena práctica dejarlo).
public interface AcopioRepository extends JpaRepository<AcopioModel, Long> {
    //Sin métodos personalizados por ahora: los heredados cubren lo que necesita el Service.
}