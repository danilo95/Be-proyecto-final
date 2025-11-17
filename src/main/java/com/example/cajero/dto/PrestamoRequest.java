package com.example.cajero.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PrestamoRequest {

    @NotNull
    private Integer idCajero;

    @NotNull
    private Integer idCliente;

    @NotNull
    private String numeroCuenta; // CAMPO NUEVO

    @NotNull
    @Min(1)
    private Double monto;

    // getters/setters
    public Integer getIdCajero() { return idCajero; }
    public void setIdCajero(Integer idCajero) { this.idCajero = idCajero; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
}