package com.example.despachoo.exception;

/**
 * Excepción personalizada para representar errores de reglas de negocio
 * (por ejemplo: un despacho en estado inválido, datos inconsistentes, etc.).
 * A diferencia de una RuntimeException genérica, esta permite especificar
 * qué código HTTP debe devolverse al cliente cuando ocurre el error
 * (ej: 400, 404, 409), en vez de que todo termine como un 500 genérico.
 */
public class NegocioException extends RuntimeException {

    //Código de estado HTTP asociado a este error de negocio.
    private final int httpStatus;

    public NegocioException(String message, int httpStatus) {
        super(message); // Se guarda el mensaje de error en la RuntimeException padre.
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}