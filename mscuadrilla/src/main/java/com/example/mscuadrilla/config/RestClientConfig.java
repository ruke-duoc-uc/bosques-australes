package com.example.mscuadrilla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
/**
 * CLASE DE CONFIGURACIÓN DE INFRAESTRUCTURA - CLIENTE HTTP DISTRIBUIDO
 * Esta clase técnica registra un Bean de tipo 'RestClient.Builder' en el contenedor de Spring Boot.
 * Su propósito de arquitectura es proveer un constructor centralizado, seguro y preconfigurado de
 * clientes HTTP síncronos. Gracias a esto, la capa de servicios (como 'CuadrillaService') puede inyectar
 * esta dependencia para despachar peticiones REST hacia otros microservicios externos (como el de Trabajadores),
 * resolviendo de manera limpia el desacoplamiento y la integración por red del ecosistema distribuido.
 */

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient.Builder restClientBuilder(){return RestClient.builder();}
}
