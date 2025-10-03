package net.xzh.fisco.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SpringDocConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI();
    }
}