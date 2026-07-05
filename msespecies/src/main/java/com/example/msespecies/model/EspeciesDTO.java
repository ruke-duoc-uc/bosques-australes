package com.example.msespecies.model;

import io.swagger.v3.oas.annotations.media.Schema;
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/

/*
    EspeciesDTO se utiliza en el metodo PATCH para evitar que se
    el cuerpo de la actualizacion sea forzado a estar completo
 */

public record EspeciesDTO(
        String nombre,
        String uso,
        String calidad,
        String color){}
