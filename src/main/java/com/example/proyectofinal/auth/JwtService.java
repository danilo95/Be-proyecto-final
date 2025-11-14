package com.example.proyectofinal.auth;
import com.example.proyectofinal.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${security.jwt.secret}") String secret) {
        // IMPORTANTE: secret debe tener al menos 32 caracteres
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Usuario usuario) {
        long now = System.currentTimeMillis();
        long expirationMs = 1000 * 60 * 60; // 1 hora

        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("id", usuario.getIdUsuario())
                .claim("rol", usuario.getRol().name())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMs))
                .signWith(key)
                .compact();
    }
}