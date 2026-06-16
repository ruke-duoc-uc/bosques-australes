package com.example.despachoo.repository;

import com.example.despachoo.model.DespachoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespachoRepository extends JpaRepository<DespachoModel, Long> {

}
