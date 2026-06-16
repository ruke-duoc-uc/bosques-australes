package com.example.mstrabajadores.repository;

import com.example.mstrabajadores.model.TrabajadoresModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TrabajadoresRepository extends JpaRepository<TrabajadoresModel, Long>{

}
