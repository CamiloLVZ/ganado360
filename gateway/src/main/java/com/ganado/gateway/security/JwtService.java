package com.ganado.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.JwtException;


@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${JWT_SECRET:ReplaceThisVeryStrongSecretKeyDontCommit}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    public boolean isTokenExpired(Claims claims) {
        Date exp = claims.getExpiration();
        return exp == null || exp.before(new Date());
    }

    public Claims getClaims(String token) throws JwtException {
        return parseToken(token).getPayload();
    }
}