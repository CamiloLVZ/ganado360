package com.ganado.reproduccion.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class InternalGatewayFilter extends OncePerRequestFilter {

    @Value("${gateway.internal.secret:Ganado360InternalSecretKey2026}")
    private String internalSecret;

    @Value("${gateway.security.enabled:true}")
    private boolean securityEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!securityEnabled) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();
        if (path.startsWith("/error") || path.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("X-Internal-Gateway-Token");
        if (token == null || !token.equals(internalSecret)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Acceso denegado: este microservicio solo acepta peticiones a través del API Gateway\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
