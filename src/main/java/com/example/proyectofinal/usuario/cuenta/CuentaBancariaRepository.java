package com.example.proyectofinal.usuario.cuenta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Integer> {

    // busca por idUsuario del cliente
    List<CuentaBancaria> findByCliente_IdUsuario(Integer idUsuario);
    Optional<CuentaBancaria> findFirstByCliente_IdUsuarioOrderByIdCuentaAsc(Integer idUsuario);
    }