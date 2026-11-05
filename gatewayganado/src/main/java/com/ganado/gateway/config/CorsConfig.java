package com.ganado.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${CORS_ORIGINS:http://localhost:5173,http://localhost}")
    private String corsOrigins;

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> patterns = new ArrayList<>(List.of("http://localhost*", "http://127.0.0.1*"));
        if (corsOrigins != null && !corsOrigins.isBlank()) {
            Arrays.stream(corsOrigins.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .forEach(patterns::add);
        }
        config.setAllowedOriginPatterns(patterns);
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
