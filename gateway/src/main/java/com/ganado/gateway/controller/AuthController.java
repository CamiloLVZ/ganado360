package com.ganado.gateway.controller;

import com.ganado.gateway.dto.ConfirmCodeDTO;
import com.ganado.gateway.dto.ErrorResponse;
import com.ganado.gateway.dto.PasswordDTO;
import com.ganado.gateway.dto.ResetPasswordDTO;
import com.ganado.gateway.dto.user.LoginDTO;
import com.ganado.gateway.dto.user.LoginResponse;
import com.ganado.gateway.dto.user.TokenResponse;
import com.ganado.gateway.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserClient userClient;

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody LoginDTO dto) {

        return userClient.validate(dto)
                .map(user -> {
                    String token = jwtService.createToken(
                            Map.of("id", user.getId()),
                            user.getEmail()
                    );

                    return ResponseEntity.ok((Object) new LoginResponse(token, user));
                })
                .onErrorResume(WebClientResponseException.Unauthorized.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body((Object) new ErrorResponse("Invalid credentials")))
                )
                .onErrorResume(WebClientResponseException.NotFound.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body((Object) new ErrorResponse("User not found")))
                )
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Service unavailable: " + e.getMessage())))
                )
                .onErrorResume(Exception.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("An unexpected error occurred")))
                );
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<?>> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Missing or invalid Authorization header")));
        }

        try {
            String oldToken = authHeader.substring(7);
            var claims = jwtService.getClaims(oldToken);

            if (jwtService.isTokenExpired(claims)) {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Token expired")));
            }

            String newToken = jwtService.createToken(
                    Map.of("id", claims.get("id")),
                    claims.getSubject()
            );

            return Mono.just(ResponseEntity.ok(new TokenResponse(newToken)));

        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid token")));
        }
    }

    @PostMapping("/reset-password")
    public Mono<ResponseEntity<Object>> resetPassword(@RequestBody ResetPasswordDTO dto) {

        return userClient.resetPassword(dto)
                .map(response -> ResponseEntity.ok((Object) response))
                .onErrorResume(WebClientResponseException.NotFound.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body((Object) new ErrorResponse("Email not found"))))
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Service unavailable: " + e.getMessage()))))
                .onErrorResume(Exception.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Unexpected error"))));
    }

    @PostMapping("/reset-password/confirm")
    public Mono<ResponseEntity<Object>> confirmResetCode(@RequestBody ConfirmCodeDTO dto) {

        return userClient.confirmResetCode(dto)
                .map(response -> ResponseEntity.ok((Object) response))
                .onErrorResume(WebClientResponseException.BadRequest.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body((Object) new ErrorResponse("Invalid or expired code"))))
                .onErrorResume(WebClientResponseException.NotFound.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body((Object) new ErrorResponse("Email not found"))))
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Service unavailable: " + e.getMessage()))))
                .onErrorResume(Exception.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Unexpected error"))));
    }

    @PostMapping("/reset-password/new")
    public Mono<ResponseEntity<Object>> setNewPassword(@RequestBody PasswordDTO dto) {

        return userClient.setNewPassword(dto)
                .map(response -> ResponseEntity.ok((Object) response))
                .onErrorResume(WebClientResponseException.BadRequest.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body((Object) new ErrorResponse("Password requirements not met"))))
                .onErrorResume(WebClientResponseException.NotFound.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body((Object) new ErrorResponse("Email not found"))))
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Service unavailable: " + e.getMessage()))))
                .onErrorResume(Exception.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body((Object) new ErrorResponse("Unexpected error"))));
    }
}