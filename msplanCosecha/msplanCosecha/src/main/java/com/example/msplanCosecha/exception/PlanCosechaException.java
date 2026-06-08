package com.example.msplanCosecha.exception;

public class PlanCosechaException extends RuntimeException {
    private final int httpStatus;

    public PlanCosechaException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
