package com.example.despachoo.exception;

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
 * Manejador global de excepciones para todo el microservicio de Despacho.
 * Centraliza la conversión de excepciones (de JPA, validaciones, negocio, etc.)
 * en respuestas HTTP consistentes, con un formato de cuerpo (body) uniforme
 * para que el frontend/cliente siempre reciba la misma estructura de error.
 */
@RestControllerAdvice //Intercepta excepciones lanzadas desde cualquier @RestController de la app.
public class GlobalExceptionsHandler {

    //Se dispara cuando no se encuentra una entidad esperada en la base de datos.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), null);
    }

    //Se dispara cuando fallan las validaciones de un @RequestBody anotado con @Valid
    //(ej: un campo @NotNull que llegó vacío).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        //Se arma una lista legible de errores tipo "campo: mensaje".
        List<String> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Errores de validación en los datos del despacho", errores);
    }

    //Se dispara cuando fallan validaciones a nivel de constraints (ej: @Validated en parámetros sueltos).
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(ConstraintViolationException ex) {
        List<String> errores = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION", "Errores de validación", errores);
    }

    //Maneja las excepciones de negocio personalizadas (NegocioException),
    //respetando el código HTTP que se definió al lanzarla.
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Map<String, Object>> handleNegocio(NegocioException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getHttpStatus());
        return buildResponse(status, "NEGOCIO_ERROR", ex.getMessage(), null);
    }

    //Catch-all: cualquier otra excepción no controlada explícitamente cae acá,
    //devolviendo un 500 en vez de exponer un stacktrace crudo al cliente.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INTERNO", "Ocurrió un error inesperado en el sistema de despachos: " + ex.getMessage(), null);
    }

    //Método privado reutilizado por todos los handlers para construir
    //una respuesta de error con formato consistente:
    //{ timestamp, status, codigo, mensaje, errores (opcional) }
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status, String codigo, String mensaje, List<String> errores) {
        Map<String, Object> body = new HashMap<>();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        body.put("timestamp", timestamp);
        body.put("status", status.value());
        body.put("codigo", codigo);
        body.put("mensaje", mensaje);
        if (errores != null) body.put("errores", errores); // Solo se agrega si hay lista de errores.

        return ResponseEntity.status(status).body(body);
    }
}