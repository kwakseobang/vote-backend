package com.kwakmunsu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
            return new OpenAPI()
                    .components(new Components())
                    .info(apiInfo());

    }

    private Info apiInfo() {
        return new Info()
                .title("Vote - Swagger API")
                .description("간단 투표")
                .version("1.0.0");
    }
}