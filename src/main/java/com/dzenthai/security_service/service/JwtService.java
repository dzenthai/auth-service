package com.dzenthai.security_service.service;

import com.dzenthai.security_service.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JwtService {

    private final SecretKey secretKey;

    private final ObjectMapper objectMapper;

    @Value("${spring.jwt.lifetime}")
    private int lifetime;

    public JwtService(
            @Value("${spring.jwt.key}") String secretKeyString,
            ObjectMapper objectMapper
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
        this.objectMapper = objectMapper;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(lifetime)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> extractRoleFromToken(String token) {
        Claims claims = getClaims(token);
        Object roles = claims.get("roles");
        if (roles == null) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.convertValue(roles, new TypeReference<>() {});
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Roles claim is not of expected type", e);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
