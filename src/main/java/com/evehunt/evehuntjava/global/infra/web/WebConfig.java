package com.evehunt.evehuntjava.global.infra.web;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns(new String[]{"*"})
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders(new String[]{"Authorization", "Content-Type", "Access-Control-Allow-Origin"})
                .exposedHeaders(new String[]{"Custom-Header"}).allowCredentials(true).maxAge(3600L);
    }
}
