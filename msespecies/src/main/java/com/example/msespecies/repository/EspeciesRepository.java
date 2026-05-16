package com.example.msespecies.repository;

import com.example.msespecies.model.Especies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspeciesRepository extends JpaRepository<Especies, Long> {

}
