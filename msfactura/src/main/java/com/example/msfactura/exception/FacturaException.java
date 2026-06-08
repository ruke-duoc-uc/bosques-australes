package com.example.msfactura.exception;

public class FacturaException extends RuntimeException {
    private final int httpStatus;
    public FacturaException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
