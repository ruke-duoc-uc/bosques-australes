package com.example.cliente.exception;

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

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Cliente no encontrado (Cuando el Service lanza EntityNotFoundException)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), null);
    }

    // 400 - Errores de validación en los DTOs (ej: Mandar un RUT o Email vacío desde Postman)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Errores de validación en los datos del cliente", errores);
    }

    // 400 - Violaciones de restricciones directas
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(ConstraintViolationException ex) {
        List<String> errores = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION", "Errores de validación", errores);
    }

    // 409 / 422 - Captura tu NegocioException (ej: El RUT duplicado que pusimos en el Service)
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Map<String, Object>> handleNegocio(NegocioException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getHttpStatus());
        return buildResponse(status, "NEGOCIO_ERROR", ex.getMessage(), null);
    }

    // 500 - El paracaídas de emergencia para cualquier otro error desconocido o caída de BD
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INTERNO", "Ocurrió un error inesperado en el sistema de clientes: " + ex.getMessage(), null);
    }

    // Método estructurador del JSON definitivo
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
