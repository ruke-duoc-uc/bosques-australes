package com.example.mstrabajadores.exception;

public class NegocioException extends RuntimeException{
    private final int httpStatus;

    public NegocioException(String messege, int httpStatus) {
        super(messege);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus(){
        return httpStatus;
    }
}
