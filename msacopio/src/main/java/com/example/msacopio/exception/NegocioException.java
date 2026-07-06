package com.example.msacopio.exception;

/**
 * Excepción personalizada para representar errores de reglas de negocio
 * del microservicio de Acopio, permitiendo definir el código HTTP
 * que debe devolverse al cliente (en vez de un 500 genérico).
 */
public class NegocioException extends RuntimeException{

    //Código de estado HTTP asociado a este error de negocio.
    private final int httpStatus;

    public NegocioException(String messege, int httpStatus){
        super(messege); //Se guarda el mensaje en la RuntimeException padre.
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}