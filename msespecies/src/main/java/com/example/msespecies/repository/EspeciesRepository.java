package com.example.msespecies.repository;

import com.example.msespecies.model.Especies;
import org.springframework.data.jpa.repository.JpaRepository;
/*
    JPA facilita la comunicacion con la base de datos, la cual es de H2.
    Usando este interface obtenemos los metodos que usa el Service, tales como:
    - .save()
    - .findById().
    - .findAll().
    Estos metodos son estandar, incluso podemos agregar otros en esta interface para que en
    el Service no tengamos que pasar por logica reduntante
*/
public interface EspeciesRepository extends JpaRepository<Especies, Long> {

}
