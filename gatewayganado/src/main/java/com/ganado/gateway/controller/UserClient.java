package com.ganado.gateway.controller;


import com.ganado.gateway.dto.user.*;
import com.ganado.gateway.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient.Builder webClientBuilder, @Value("${user.service.url}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl) // microservicio de usuarios
                .build();
    }

    // ✔ LOGIN
    public Mono<Usuario> validate(LoginDTO dto) {
        return webClient.post()
                .uri("/api/users/validate")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Usuario.class);
    }

    // ✔ REGISTER
    public Mono<Usuario> create(RegisterDTO dto) {
        return webClient.post()
                .uri("/api/users/register")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Usuario.class);
    }

    // ✔ GET ALL (usa UserResponseDTO)
    public Flux<UserResponseDTO> findAll() {
        return webClient.get()
                .uri("/api/users")
                .retrieve()
                .bodyToFlux(UserResponseDTO.class);
    }

    // ✔ GET BY ID
    public Mono<UserResponseDTO> findById(UUID id) {
        return webClient.get()
                .uri("/api/users/{id}", id)
                .retrieve()
                .bodyToMono(UserResponseDTO.class);
    }

    public Mono<UserResponseDTO> findByEmail(String email) {
        return webClient.get()
                .uri("/api/users/email/{email}", email)
                .retrieve()
                .bodyToMono(UserResponseDTO.class);
    }

    // ✔ UPDATE (usa UserRequestDTO)
    public Mono<UserResponseDTO> update(UUID id, UserRequestDTO dto) {
        return webClient.put()
                .uri("/api/users/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(UserResponseDTO.class);
    }

    // ✔ DELETE
    public Mono<Void> delete(UUID id) {
        return webClient.delete()
                .uri("/api/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // ✔ CHECK EMAIL
    public Mono<Boolean> checkEmail(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/users/checkemail")
                        .queryParam("email", email)
                        .build()
                )
                .retrieve()
                .bodyToMono(CheckEmailResponse.class)
                .map(CheckEmailResponse::isExists);
    }
}