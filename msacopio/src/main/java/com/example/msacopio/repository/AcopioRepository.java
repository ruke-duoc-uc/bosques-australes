package com.example.msacopio.repository;

import com.example.msacopio.model.AcopioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcopioRepository extends JpaRepository<AcopioModel, Long> {

}
