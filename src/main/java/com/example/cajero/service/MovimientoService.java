package com.example.cajero.service;

import org.springframework.stereotype.Service;

import com.example.cajero.entity.Movimiento;
import com.example.cajero.repository.MovimientoRepository;

import java.util.List;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepo;

    public MovimientoService(MovimientoRepository movimientoRepo) {
        this.movimientoRepo = movimientoRepo;
    }

    public List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta) {
        return movimientoRepo.findByCuenta_NumeroCuenta(numeroCuenta);
    }

    public List<Movimiento> obtenerMovimientosPorCliente(Integer idCliente) {
        return movimientoRepo.findByCuenta_Cliente_IdUsuario(idCliente);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        return movimientoRepo.save(movimiento);
    }
}