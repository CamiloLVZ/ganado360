package com.ganado.gateway.controller;

import com.ganado.gateway.dto.user.LoginDTO;
import com.ganado.gateway.dto.user.RegisterDTO;
import com.ganado.gateway.dto.user.UserRequestDTO;
import com.ganado.gateway.dto.user.UserResponseDTO;
import com.ganado.gateway.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.UUID;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    // ✔ GET ALL → usa UserResponseDTO
    @GetMapping
    public Flux<UserResponseDTO> findAll() {
        return userClient.findAll();
    }

    // ✔ GET BY ID
    @GetMapping("/{id}")
    public Mono<UserResponseDTO> findById(@PathVariable UUID id) {
        return userClient.findById(id);
    }

    // ✔ CREATE (REGISTER)
    @PostMapping("/register")
    public Mono<Usuario> create(@RequestBody RegisterDTO dto) {
        return userClient.create(dto);
    }

    // ✔ LOGIN
    @PostMapping("/validate")
    public Mono<Usuario> validate(@RequestBody LoginDTO dto) {
        return userClient.validate(dto);
    }

    // ✔ UPDATE (usa UserRequestDTO, no RegisterDTO)
    @PutMapping("/{id}")
    public Mono<UserResponseDTO> update(@PathVariable UUID id,
                                        @RequestBody UserRequestDTO dto) {
        return userClient.update(id, dto);
    }

    // ✔ DELETE
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return userClient.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    // ✔ CHECK EMAIL
    @GetMapping("/checkemail")
    public Mono<Boolean> checkEmail(@RequestParam String email) {
        return userClient.checkEmail(email);
    }
}
