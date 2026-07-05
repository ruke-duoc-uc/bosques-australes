package com.example.msplanCosecha.client;
//  EspeciesClient deriva los datos a Spring, quien los traducira e
//  instanciara a este record
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/
public record EspeciesDTO (
    Long id,
    String nombre
){}
