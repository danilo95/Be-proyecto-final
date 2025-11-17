package com.example.cajero.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovimientoRequest {

    @NotNull
    private Integer idCajero;

    @NotBlank
    private String duiCliente;

    @NotBlank
    private String numeroCuenta; // CAMPO NUEVO

    @NotNull
    @Min(0)
    private Double monto;

    @NotBlank
    private String tipo; // CAMPO NUEVO: "DEPOSITO" o "RETIRO"

    // getters/setters
    public Integer getIdCajero() { return idCajero; }
    public void setIdCajero(Integer idCajero) { this.idCajero = idCajero; }

    public String getDuiCliente() { return duiCliente; }
    public void setDuiCliente(String duiCliente) { this.duiCliente = duiCliente; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}