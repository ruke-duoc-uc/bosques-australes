package com.example.mspredios.model;
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/

/*
    PrediosDTO se utiliza en el metodo PATCH para evitar que se
    el cuerpo de la actualizacion sea forzado a estar completo
 */
public record PrediosDTO(
    String nombre,
    String ciudad,
    String comuna,
    String direccion
) {
}
