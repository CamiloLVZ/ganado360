package com.ganado.reproduccion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF (necesario para APIs)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // ⬅️ LIBERA TODOS LOS ENDPOINTS
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // Desactiva basic auth
                .formLogin(form -> form.disable()); // Desactiva login con formulario

        return http.build();
    }
}

