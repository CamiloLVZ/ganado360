package com.ganado.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter, Ordered {

    private final JwtService jwtService;
    private final String corsOrigins;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/refresh",
            "/api/users/register",
            "/api/users/checkemail",
            "/api/users/reset-password",
            "/api/users/reset-password/confirm",
            "/api/users/reset-password/new"
    );

    public JwtAuthenticationFilter(
            JwtService jwtService,
            @Value("${CORS_ORIGINS:http://localhost:5173,http://localhost}") String corsOrigins
    ) {
        this.jwtService = jwtService;
        this.corsOrigins = corsOrigins;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 1) Preflight OPTIONS delegados a CorsWebFilter
        if ("OPTIONS".equalsIgnoreCase(request.getMethod().name())) {
            return chain.filter(exchange);
        }

        // 2) RUTAS PÚBLICAS
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

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

            String userId = claims.get("id") != null ? String.valueOf(claims.get("id")) : null;
            String userEmail = claims.getSubject();

            // Pasar información del usuario a otros microservicios
            ServerHttpRequest.Builder reqBuilder = request.mutate();
            if (userId != null) {
                reqBuilder.header("X-Auth-User-Id", userId);
            }
            if (userEmail != null) {
                reqBuilder.header("X-Auth-User-Email", userEmail);
            }
            ServerHttpRequest newRequest = reqBuilder.build();

            return chain.filter(exchange.mutate().request(newRequest).build())
                    .contextWrite(ctx -> {
                        Context c = ctx;
                        if (userId != null) {
                            c = c.put("authUserId", userId);
                        }
                        if (userEmail != null) {
                            c = c.put("authUserEmail", userEmail);
                        }
                        return c;
                    });

        } catch (JwtException e) {
            return unauthorized(exchange, "Invalid token: " + e.getMessage());
        }
    }

    // -----------------------------
    // MÉTODOS AUXILIARES
    // -----------------------------

    private void addCorsHeaders(ServerHttpRequest request, ServerHttpResponse response) {
        String origin = request.getHeaders().getOrigin();
        if (origin != null && !origin.isBlank()) {
            response.getHeaders().set("Access-Control-Allow-Origin", origin);
            response.getHeaders().set("Access-Control-Allow-Credentials", "true");
            response.getHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Auth-User-Id, X-Auth-User-Email");
            response.getHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        addCorsHeaders(exchange.getRequest(), response);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        byte[] bytes = String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type", "application/json");

        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100; // Prioridad alta: se ejecuta antes que los handlers de controlador
    }
}
