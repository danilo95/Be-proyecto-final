package com.example.cajero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cajero.entity.CuentaBancaria;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaBancaria, Integer> {

    // MÉTODOS EXISTENTES
    List<CuentaBancaria> findByCliente_IdUsuario(Integer idUsuario);
    Optional<CuentaBancaria> findByNumeroCuenta(String numeroCuenta);

    // MÉTODOS NUEVOS AGREGADOS (nombres corregidos)
    List<CuentaBancaria> findByCliente_Dui(String dui);
    int countByCliente_IdUsuario(Integer idUsuario); // CORREGIDO
    boolean existsByNumeroCuenta(String numeroCuenta);
}