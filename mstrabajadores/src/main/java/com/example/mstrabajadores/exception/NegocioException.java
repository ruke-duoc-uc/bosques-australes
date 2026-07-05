package com.example.mstrabajadores.exception;

/**
 * Excepción personalizada para representar errores de reglas de negocio
 * del microservicio de Trabajadores (ej: RUT duplicado al registrar),
 * permitiendo definir el código HTTP que debe devolverse al cliente.
 */
public class NegocioException extends RuntimeException{

    //Código de estado HTTP asociado a este error de negocio.
    private final int httpStatus;

    public NegocioException(String messege, int httpStatus) {
        super(messege); //Se guarda el mensaje en la RuntimeException padre.
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus(){
        return httpStatus;
    }
}