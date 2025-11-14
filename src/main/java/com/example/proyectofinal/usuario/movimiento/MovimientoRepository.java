package com.example.proyectofinal.usuario.movimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    // todos los movimientos de una cuenta que sean del mismo usuario por id
    List<Movimiento> findByCuenta_IdCuenta(Integer idCuenta);
}
