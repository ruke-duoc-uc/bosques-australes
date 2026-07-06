package com.example.despachoo.model;

/**
 * Representa el tipo de pedido asociado a un despacho.
 * Actualmente solo contempla un valor, pero está pensado para
 * ampliarse a futuro con otros tipos (ej: NACIONAL, LOCAL, etc.).
 */
public enum tipoPedido {
    EXPORTACION;  //Pedido destinado a exportación.

    private tipoPedido() {
    }
}