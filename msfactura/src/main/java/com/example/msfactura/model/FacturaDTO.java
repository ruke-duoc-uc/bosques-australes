package com.example.msfactura.model;

public record FacturaDTO(
    Long idPredio,
    Long idCLiente,
    // Atributos propios
    Long numFactura,
    String giro,
    Double monto
) {
}
