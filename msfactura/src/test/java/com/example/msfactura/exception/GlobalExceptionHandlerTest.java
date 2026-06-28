package com.example.msfactura.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    // CONTROLADOR FICTICIO ASOCIADO AL ADVICE DE TU PROPIO PAQUETE
    @RestController
    private static class ExceptionTestController {

        @GetMapping("/test/404")
        public void throwNotFound() {
            throw new EntityNotFoundException("Factura no encontrada en el sistema corporativo");
        }

        @GetMapping("/test/negocio")
        public void throwNegocio() {
            throw new FacturaException("Fallo de regla de negocio simulado", 409);
        }

        @GetMapping("/test/500")
        public void throwGeneral() {
            throw new RuntimeException("Error general de conexion o timeout en DB");
        }

        @GetMapping("/test/400-valid")
        public void throwValidation() throws MethodArgumentNotValidException, NoSuchMethodException {
            var bindingResult = new BeanPropertyBindingResult(new Object(), "factura");
            bindingResult.addError(new FieldError("factura", "monto", "El monto ingresado no puede ser negativo"));

            // Búsqueda segura por nombre para blindar JaCoCo contra ordenamientos de la JVM
            Method method = this.getClass().getMethod("throwValidation");

            throw new MethodArgumentNotValidException(
                    new MethodParameter(method, -1),
                    bindingResult
            );
        }

        @GetMapping("/test/400-constraint")
        public void throwConstraint() {
            throw new ConstraintViolationException("Violación detectada", Collections.emptySet());
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExceptionTestController())
                .setControllerAdvice(new com.example.msfactura.exception.GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Debe capturar EntityNotFoundException y retornar 404")
    void debeManejarNotFound() throws Exception {
        mockMvc.perform(get("/test/404"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("Factura no encontrada en el sistema corporativo"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Debe capturar MethodArgumentNotValidException y retornar 400")
    void debeManejarValidation() throws Exception {
        mockMvc.perform(get("/test/400-valid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.errores").isArray());
    }

    @Test
    @DisplayName("Debe capturar ConstraintViolationException y retornar 400")
    void debeManejarConstraint() throws Exception {
        mockMvc.perform(get("/test/400-constraint"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("CONSTRAINT_VIOLATION"));
    }

    @Test
    @DisplayName("Debe capturar NegocioException y retornar 409")
    void debeManejarNegocio() throws Exception {
        mockMvc.perform(get("/test/negocio"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.codigo").value("NEGOCIO_ERROR"));
    }

    @Test
    @DisplayName("Debe capturar cualquier Exception genérica y retornar 500")
    void debeManejarGeneral() throws Exception {
        mockMvc.perform(get("/test/500"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.codigo").value("ERROR_INTERNO"));
    }
}