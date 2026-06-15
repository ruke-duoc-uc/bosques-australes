package com.example.msespecies.exception;

public class EspeciesException extends RuntimeException{
    private final int httpStatus;
    public EspeciesException(String message, int httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
