package com.example.cliente.repository;

import com.example.cliente.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEstado(boolean estado);

    List<Cliente> findByNombre(String nombre);
}
