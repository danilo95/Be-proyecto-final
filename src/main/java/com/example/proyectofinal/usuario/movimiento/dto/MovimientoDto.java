package com.example.proyectofinal.usuario.movimiento.dto;

import com.example.proyectofinal.usuario.movimiento.TipoMovimiento;

import java.time.LocalDateTime;

public class MovimientoDto {

    private Integer idMovimiento;
    private TipoMovimiento tipo;
    private Double monto;
    private LocalDateTime fecha;
    private Integer idCuenta;

    public Integer getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Integer idMovimiento) { this.idMovimiento = idMovimiento; }

    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Integer getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Integer idCuenta) { this.idCuenta = idCuenta; }
}
