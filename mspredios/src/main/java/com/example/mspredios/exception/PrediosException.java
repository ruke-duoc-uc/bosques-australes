package com.example.mspredios.exception;

public class PrediosException extends RuntimeException {
    private final int httpStatus;

    public PrediosException(String message, int httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
