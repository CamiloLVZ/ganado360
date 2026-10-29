package com.ganado.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;
    private final String corsOrigins;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/login",
            "/api/users/register",
            "/api/users/checkemail",
            "/api/auth/reset-password",
            "/api/auth/reset-password/confirm",
            "/api/auth/reset-password/new",
            "/api/user/reset-password",
            "/api/user/reset-password/confirm",
            "/api/user/reset-password/new"
    );

    public JwtAuthenticationFilter(
            JwtService jwtService,
            @Value("${CORS_ORIGINS:http://localhost:5173}") String corsOrigins
    ) {
        this.jwtService = jwtService;
        this.corsOrigins = corsOrigins;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        System.out.println("[GATEWAY] Nueva request: " + request.getMethod() + " " + path);
        System.out.println("[GATEWAY] Headers: " + request.getHeaders());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod().name())) {
            System.out.println("[GATEWAY] OPTIONS preflight detectado -> OK");
            ServerHttpResponse response = exchange.getResponse();
            addCorsHeaders(response);
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // 2) RUTAS PÚBLICAS
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        System.out.println("[GATEWAY] Validando token...");
        // 3) VALIDACIÓN JWT
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.getClaims(token);

            if (jwtService.isTokenExpired(claims)) {
                return unauthorized(exchange, "Token expired");
            }

            // Pasar información del usuario a otros microservicios
            ServerHttpRequest newRequest = request.mutate()
                    .header("X-Auth-User-Id", String.valueOf(claims.get("id")))
                    .header("X-Auth-User-Email", claims.getSubject())
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());

        } catch (JwtException e) {
            System.out.println("[GATEWAY] Token inválido: " + e.getMessage());
            return unauthorized(exchange, "Invalid token");
        }
    }


    // -----------------------------
    // MÉTODOS AUXILIARES
    // -----------------------------

    private void addCorsHeaders(ServerHttpResponse response) {
        response.getHeaders().add("Access-Control-Allow-Origin", corsOrigins);
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        System.out.println("[GATEWAY] Respondiendo 401: " + message);
        ServerHttpResponse response = exchange.getResponse();
        addCorsHeaders(response);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        // Cambia esto para ejecutarse ANTES (número más bajo = mayor prioridad)
        return -100; // Se ejecuta antes que otros filtros
    }
}