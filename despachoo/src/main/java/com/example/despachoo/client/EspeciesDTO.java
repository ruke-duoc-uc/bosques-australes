package com.example.despachoo.client;

/**
 * DTO (Data Transfer Object) que representa la información de una especie
 * tal como la entrega el microservicio de Especies.
 * Al ser un record, Java genera automáticamente:
 *   - Constructor con los dos parámetros.
 *   - Getters con la forma id() y nombre() (no getId()/getNombre()).
 *   - equals(), hashCode() y toString().
 * Es un objeto inmutable: una vez creado, sus valores no cambian.
 */
public record EspeciesDTO(Long id, String nombre) {
}