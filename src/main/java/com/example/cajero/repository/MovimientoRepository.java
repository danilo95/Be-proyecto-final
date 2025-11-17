package com.example.cajero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cajero.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    // MÉTODO EXISTENTE
    List<Movimiento> findByCuenta_IdCuentaOrderByFechaDesc(Integer idCuenta);

    // MÉTODOS NUEVOS AGREGADOS (nombres corregidos)
    List<Movimiento> findByCuenta_NumeroCuenta(String numeroCuenta);
    List<Movimiento> findByCuenta_Cliente_IdUsuario(Integer idCliente); // CORREGIDO
    List<Movimiento> findByUsuario_IdUsuario(Integer idUsuario);
    List<Movimiento> findByCuenta_NumeroCuentaOrderByFechaDesc(String numeroCuenta);
}