package com.example.cajero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cajero.entity.Rol;
import com.example.cajero.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // MÉTODOS EXISTENTES
    Optional<Usuario> findByDui(String dui);
    Optional<Usuario> findByCorreo(String correo);

    // MÉTODOS NUEVOS AGREGADOS
    List<Usuario> findByRol(Rol rol);
    List<Usuario> findByRolAndActivoTrue(Rol rol);
    List<Usuario> findByActivoTrue();
    boolean existsByDui(String dui);
    List<Usuario> findByRolOrderByNombreAsc(Rol rol);
}