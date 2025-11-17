package com.example.cajero.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CrearCuentaRequest {

    @NotNull
    private Integer idCliente;

    @NotBlank
    private String tipoCuenta; // "AHORRO" o "CORRIENTE"

    // getters/setters
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
}