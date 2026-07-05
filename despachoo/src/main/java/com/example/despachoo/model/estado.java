package com.example.despachoo.model;

/**
 * Representa los posibles estados por los que puede pasar un despacho
 * a lo largo de su ciclo de vida.
 */
public enum estado {
    GENERADA,   //El despacho fue creado pero aún no ha salido.
    TRANSITO,   //El despacho está en camino hacia su destino.
    ENTREGADA,  //El despacho llegó correctamente a destino.
    CANCELADA;  //El despacho fue cancelado antes de completarse.

    //Constructor vacío del enum (no es necesario declararlo explícitamente,
    //Java lo genera solo, pero no afecta que esté aquí).
    private estado() {
    }
}