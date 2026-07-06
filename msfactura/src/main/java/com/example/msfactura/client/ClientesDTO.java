package com.example.msfactura.client;

//  ClientesClient deriva los datos a Spring, quien los traducira e
//  instanciara a este record
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/
public record ClientesDTO(
        String razonSocial,
        String comuna,
        String ciudad,
        String telefono
) {}
