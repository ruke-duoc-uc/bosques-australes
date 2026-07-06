package com.example.despachoo.model;

/**
 * Representa los posibles lugares físicos donde se puede recepcionar
 * el pedido asociado a un despacho.
 */
public enum lugarRecepcion {
    PREDIOS,  //Recepción directamente en los predios/terrenos forestales.
    BODEGA,   //Recepción en bodega.
    OFICINA;  //Recepción en oficina.

    private lugarRecepcion() {
    }
}