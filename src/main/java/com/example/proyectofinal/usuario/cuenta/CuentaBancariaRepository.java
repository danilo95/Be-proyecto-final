package com.example.proyectofinal.usuario.cuenta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Integer> {

    // busca por idUsuario del cliente
    List<CuentaBancaria> findByCliente_IdUsuario(Integer idUsuario);
    }