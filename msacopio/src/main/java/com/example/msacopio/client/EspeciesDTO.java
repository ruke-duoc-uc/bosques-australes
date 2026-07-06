package com.example.msacopio.client;

/**
 * DTO que representa los datos de una especie tal como los entrega
 * el microservicio de Especies. Objeto inmutable (record).
 */
public record EspeciesDTO(
        Long id,
        String nombre
) {
}