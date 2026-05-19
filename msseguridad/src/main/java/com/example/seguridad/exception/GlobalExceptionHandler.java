package com.example.seguridad.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice; // Cambiado a RestControllerAdvice

import java.text.SimpleDateFormat; // Para manejar el String de fecha
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice // Es mejor usar Advice para que capture errores de todos los controllers
public class GlobalExceptionHandler {

    // 404 - Entidad no encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), null);
    }

    // 400 - Violaciones de @Valid en @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Errores de validación", errores);
    }

    // 400 - Violaciones de @Validated en parámetros
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(ConstraintViolationException ex) {
        List<String> errores = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION", "Errores de validación", errores);
    }

    // 409 / 422 - Reglas de negocio (Asegúrate de tener creada la clase NegocioException)
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Map<String, Object>> handleNegocio(NegocioException ex) {
        // Asumimos que NegocioException tiene un método para obtener el código HTTP
        HttpStatus status = HttpStatus.CONFLICT; // Por defecto 409
        return buildResponse(status, "NEGOCIO_ERROR", ex.getMessage(), null);
    }

    // 500 - Error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INTERNO", "Ocurrió un error inesperado: " + ex.getMessage(), null);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status, String codigo, String mensaje, List<String> errores) {
        Map<String, Object> body = new HashMap<>();

        // Creamos el timestamp como String usando SimpleDateFormat
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        body.put("timestamp", timestamp);
        body.put("status", status.value());
        body.put("codigo", codigo);
        body.put("mensaje", mensaje);
        if (errores != null) body.put("errores", errores);

        return ResponseEntity.status(status).body(body);
    }
}