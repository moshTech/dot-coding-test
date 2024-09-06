package com.mosh.dot_assessment_test.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Configuration
public class SwaggerConfig {
    @Value("${swagger.server.url:http://localhost:8083}")
    private String swaggerUrl;
    @Bean
    public OpenAPI springShopOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("API Docs")
                        .description("This documentation contains the APIs exposed")
                        .version("v1.0.0")
                        .license(new License().name("MIT License").url("https://choosealicense.com/licenses/mit/")))
                .servers(List.of(new Server().description("Server").url(swaggerUrl)))
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(securitySchemeName))
                .components(new Components());
    }

    }
