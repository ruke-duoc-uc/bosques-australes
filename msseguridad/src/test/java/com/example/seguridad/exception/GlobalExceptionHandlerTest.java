package com.example.seguridad.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    // CONTROLADOR FICTICIO PARA DISPARAR LOS MÉTODOS DEL ADVICE
    @RestController
    private static class ExceptionTestController {
        @GetMapping("/test/404")
        public void throwNotFound() {
            throw new EntityNotFoundException("Entidad simulada no encontrada");
        }

        @GetMapping("/test/negocio")
        public void throwNegocio() {
            // Le agregamos el código 409 (o el número que corresponda a tu constructor)
            throw new NegocioException("Fallo de regla de negocio simulado", 409);
        }

        @GetMapping("/test/500")
        public void throwGeneral() {
            throw new RuntimeException("Explosión inesperada interna");
        }

        @GetMapping("/test/400-valid")
        public void throwValidation() throws MethodArgumentNotValidException {
            var bindingResult = new BeanPropertyBindingResult(new Object(), "objeto");
            bindingResult.addError(new FieldError("objeto", "campo", "No puede estar vacío"));

            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethods()[0], -1),
                    bindingResult
            );
        }

        @GetMapping("/test/400-constraint")
        public void throwConstraint() {
            // Pasamos un Set vacío para evitar problemas de stubs finales de Mockito
            // Esto obligará a tu handler a procesar el stream vacío y retornar el 400 sin caer en 500
            throw new ConstraintViolationException("Violación detectada", java.util.Collections.emptySet());
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExceptionTestController())
                .setControllerAdvice(new GlobalExceptionHandler()) // Amarramos tu Handler real
                .build();
    }

    @Test
    @DisplayName("Debe capturar EntityNotFoundException y retornar 404")
    void debeManejarNotFound() throws Exception {
        mockMvc.perform(get("/test/404"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("Entidad simulada no encontrada"))
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