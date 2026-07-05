package com.example.despachoo.repository;

import com.example.despachoo.model.DespachoModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de acceso a datos para la entidad DespachoModel.
 * Al extender de JpaRepository, Spring Data JPA nos genera automaticamente
 * (sin necesidad de implementar nada) los metodos CRUD más comunes, como:
 * -findall()
 * -findById(Long id)
 * -save(DespachoModel despacho)
 * -deleteById(Long id)
 * -existsById(Long id)
 *
 * JpaRepository<DespachoModel, Long> indica:
 * -DespachoModel: la entidad administra este repositorio.
 * -Long : el tipo de dato de la llave primaria (id) de esta entidad.
 */

public interface DespachoRepository extends JpaRepository<DespachoModel, Long> {
    // No necesitamos declarar métodos propios por ahora, ya que
    // los heredados de JpaRepository cubren todas las operaciones que usa el Service.
}
