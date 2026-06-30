package com.example.msespecies.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @RestController
    private static class ExceptionTestController {

        @GetMapping("/test/404")
        public void throwNotFound() {
            throw new EntityNotFoundException("Especie no encontrada");
        }

        @GetMapping("/test/especies-exception")
        public void throwEspecies() {
            throw new EspeciesException("Error en la regla de negocio de la especie", 409);
        }

        @GetMapping("/test/500")
        public void throwGeneral() {
            throw new RuntimeException("Fallo crítico del sistema");
        }

        @GetMapping("/test/400-valid")
        public void throwValidation() throws MethodArgumentNotValidException {
            var bindingResult = new BeanPropertyBindingResult(new Object(), "especie");
            bindingResult.addError(new FieldError("especie", "nombre", "El nombre es obligatorio"));

            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethods()[3], -1),
                    bindingResult
            );
        }

        @GetMapping("/test/400-constraint")
        public void throwConstraint() {
            throw new ConstraintViolationException("Violación de restricciones", Collections.emptySet());
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExceptionTestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Debe capturar EntityNotFoundException y retornar 404")
    void debeManejarNotFound() throws Exception {
        mockMvc.perform(get("/test/404"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("Especie no encontrada"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Debe capturar MethodArgumentNotValidException y retornar 400 con lista de errores")
    void debeManejarValidation() throws Exception {
        mockMvc.perform(get("/test/400-valid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.mensaje").value("Errores de validación"))
                .andExpect(jsonPath("$.errores").isArray())
                .andExpect(jsonPath("$.errores[0]").value("nombre: El nombre es obligatorio"));
    }

    @Test
    @DisplayName("Debe capturar ConstraintViolationException y retornar 400")
    void debeManejarConstraint() throws Exception {
        mockMvc.perform(get("/test/400-constraint"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.codigo").value("CONSTRAINT_VIOLATION"))
                .andExpect(jsonPath("$.errores").isArray());
    }

    @Test
    @DisplayName("Debe capturar EspeciesException y retornar 409 Conflict")
    void debeManejarEspeciesException() throws Exception {
        mockMvc.perform(get("/test/especies-exception"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.codigo").value("NEGOCIO_ERROR"))
                .andExpect(jsonPath("$.mensaje").value("Error en la regla de negocio de la especie"));
    }

    @Test
    @DisplayName("Debe capturar Exception genérica y retornar 500")
    void debeManejarGeneral() throws Exception {
        mockMvc.perform(get("/test/500"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.codigo").value("ERROR_INTERNO"))
                .andExpect(jsonPath("$.mensaje").value("Ocurrió un error inesperado: Fallo crítico del sistema"));
    }
}