package com.aimarketplace.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("AI Marketplace API")
                                .version("1.0")
                                .description(
                                        "Backend REST API for an AI Marketplace " +
                                                "with AI-powered tool recommendations."
                                )
                                .contact(
                                        new Contact()
                                                .name("Sohail Shaik")
                                )
                );
    }
}