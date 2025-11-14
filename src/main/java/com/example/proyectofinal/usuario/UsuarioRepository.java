package com.example.proyectofinal.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    // si quisieras permitir correo o dui:
    // Optional<Usuario> findByCorreoOrDui(String correo, String dui);
}
