package com.example.mscuadrilla.repository;

import com.example.mscuadrilla.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CAPA DE PERSISTENCIA (REPOSITORIO DATA JPA)
 * Interfaz encargada de gestionar el acceso y la persistencia de datos para la entidad 'Cuadrilla'.
 * Al extender de 'JpaRepository', Spring Boot implementa automáticamente en tiempo de ejecución
 * todos los métodos necesarios para interactuar con la base de datos (guardar, buscar, listar, eliminar),
 * abstrayendo por completo el uso de sentencias SQL manuales o conexiones JDBC.
 */
public interface CuadrillaRepository extends JpaRepository<Cuadrilla, Long>{
}
