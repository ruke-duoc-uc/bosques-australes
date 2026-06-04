package com.example.cliente.exception;

public class NegocioException extends RuntimeException {
    private final int httpStatus;

    public NegocioException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() { return httpStatus; }
}
