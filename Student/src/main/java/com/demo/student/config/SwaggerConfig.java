package com.demo.student.config;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(Collections.emptyList())
                .info(new Info()
                .title("Student Management API")
                .version("1.0")
                .description("API documentation for the Student Management System")

        );
    }
}