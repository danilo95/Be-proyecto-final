package com.example.cajero.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransaccionRequest {

    @NotBlank
    private String dui;

    @NotBlank
    private String numeroCuenta;

    @NotNull
    @Min(1)
    private Double monto;

    @NotBlank
    private String tipo; // "DEPOSITO" o "RETIRO"

    // getters/setters
    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}