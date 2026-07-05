package com.example.mscuadrilla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * CLASE DE CONFIGURACIÓN DE SEGURIDAD - SPRING SECURITY PERMISOS GLOBALES
 * Esta clase técnica configura el cortafuegos y el ciclo de filtros de seguridad (Security Filter Chain)
 * para el microservicio de Cuadrillas.
 * En entornos de desarrollo y arquitecturas distribuidas, su propósito es deshabilitar temporalmente
 * la protección CSRF (Cross-Site Request Forgery), habilitar el uso de IFrames para la consola de base de datos H2,
 * y declarar rutas de acceso libre (públicas) para que la documentación de Swagger UI, OpenAPI y los endpoints operativos
 * puedan ser consumidos sin restricciones de autenticación por otros componentes o pasarelas del sistema.
 */
@Configuration
@EnableWebSecurity
@Component // <-- Forzamos a Spring a que lea esta clase sí o sí
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Se deshabilita la protección CSRF ya que el microservicio expone una API REST sin estado (Stateless)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Abrimos paso a todas las variantes de Swagger y OpenAPI
                        .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Acceso liberado para la interfaz gráfica de la base de datos en memoria H2 de desarrollo
                        .requestMatchers("/h2-console/**").permitAll()
                        // Apertura pública de los endpoints del negocio para pruebas y libre consumo distribuido
                        .requestMatchers("/api/v1/cuadrillas/**").permitAll()
                        // Cualquier otra ruta imprevista requerirá obligatoriamente que el cliente esté autenticado
                        .anyRequest().authenticated()
                )
                // Solución técnica requerida por la consola H2: deshabilita la restricción de empaquetar la página en IFrames
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return httpSecurity.build();
    }
}