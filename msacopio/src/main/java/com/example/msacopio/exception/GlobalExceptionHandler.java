package com.example.msacopio.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manejador global de excepciones del microservicio de Acopio.
 * Mismo patrón que el usado en "despachoo": centraliza la conversión
 * de excepciones en respuestas HTTP con formato consistente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Se dispara cuando buscarPorId() (u otro método) no encuentra la entidad.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), null);
    }

    //Se dispara cuando falla la validación de un @RequestBody anotado con @Valid.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Errores de validación en los datos del acopio", errores);
    }

    //Se dispara cuando fallan validaciones a nivel de constraints.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(ConstraintViolationException ex) {
        List<String> errores = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION", "Errores de validación", errores);
    }

    //Maneja excepciones de negocio personalizadas, respetando el código HTTP definido al lanzarla.
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Map<String, Object>> handleNegocio(NegocioException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getHttpStatus());
        return buildResponse(status, "NEGOCIO_ERROR", ex.getMessage(), null);
    }

    //Catch-all: cualquier excepción no controlada explícitamente termina en un 500.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INTERNO", "Ocurrió un error inesperado en el sistema de acopio: " + ex.getMessage(), null);
    }

    //Arma la respuesta de error con formato consistente:
    //{ timestamp, status, codigo, mensaje, errores (opcional) }
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status, String codigo, String mensaje, List<String> errores) {
        Map<String, Object> body = new HashMap<>();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        body.put("timestamp", timestamp);
        body.put("status", status.value());
        body.put("codigo", codigo);
        body.put("mensaje", mensaje);
        if (errores != null) body.put("errores", errores);

        return ResponseEntity.status(status).body(body);
    }
}