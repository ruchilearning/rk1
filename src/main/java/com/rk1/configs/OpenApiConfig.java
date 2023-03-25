package com.rk1.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        return new OpenAPIV3Parser().read("apiSpec/openapi.yml");
    }
}
