package com.example.proyectofinal.usuario.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenTransaccionRepository extends JpaRepository<TokenTransaccion, Integer> {

    Optional<TokenTransaccion> findByToken(String token);
}
