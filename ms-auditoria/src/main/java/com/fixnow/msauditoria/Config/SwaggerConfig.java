package com.fixnow.msauditoria.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentación API Auditoría")
                        .version("1.0")
                        .description("Glosario completo de endpoints para el microservicio de logs y auditoría"));
    }
}
