package
com.example.cliente.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configurarOpenApi() {
        // Información de contacto
        Contact contacto = new Contact()
                .name("Bosques Autrales Repositorio")
                .url("https://github.com/ruke-duoc-uc/bosques-australes");

        // Información principal de la API
        Info informacionApi = new Info()
                .description("""
                        API para la empresa
                        Bosques Australes
                        """)
                .version("1.0")
                .termsOfService("https://www.duoc.cl")
                .contact(contacto);
        // Documentación externa (GitHub)
        ExternalDocumentation github = new ExternalDocumentation()
                .description("Repositorio oficial del proyecto Bosques Australes")
                .url("https://github.com/ruke-duoc-uc/bosques-australes");
        // Configuración OpenAPI
        return new OpenAPI()
                .info(informacionApi)
                .externalDocs(github);
    }
}
