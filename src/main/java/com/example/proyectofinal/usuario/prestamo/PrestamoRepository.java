package com.example.proyectofinal.usuario.prestamo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

   
    List<Prestamo> findByCliente_IdUsuario(Integer idUsuario);
}
