package com.example.msfactura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
/*
    @Configuration es una marca que Spring lee al arrancar la aplicacion,
    hace que esta clase sea procesada en el arranque, permitiendo que los
    metodos @Bean se ejecuten en esta fase
 */
@Configuration
public class RestClientConfig {
    /*
        @Bean es la marca que da la responsabilidad de manejar el metodo a Spring cuando
        la aplicacise apoya de las clases on arranque, en este caso Spring permitira
        que la clase Controller se pueda comunicar con los client para comunicarse con
        otros micrservicios.
        Si bien el config tiene un cuerpo vacio, este apoya a las clases Client para que
        puedan funcionar, dando una base global a todas en el arranque
     */
    @Bean
    // RestClient es la parte que se encarga de las consultas HTTP
    // .Builder se encarga de mutar la construccion en los Client segun sus microservicios
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}