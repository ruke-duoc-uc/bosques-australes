package com.example.msfactura.model;
/*
    Record es una clase que maneja sus atributos rapidamente
    creandolos como private final para mantenerlos inmutables,
    ademas de darles un contructor y getters
*/

/*
    FacturaDTO se utiliza en el metodo PATCH para evitar que se
    el cuerpo de la actualizacion sea forzado a estar completo
 */
public record FacturaDTO(
    Long idPredio,
    Long idCliente,
    // Atributos propios
    Long numFactura,
    String giro,
    Double monto
) {
}
