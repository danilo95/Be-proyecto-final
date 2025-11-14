package com.example.proyectofinal.usuario.retiro.dto;

public class ConfirmarRetiroResponse {

    private Integer idCuenta;
    private Double montoRetirado;
    private Double saldoFinal;

    public ConfirmarRetiroResponse(Integer idCuenta, Double montoRetirado, Double saldoFinal) {
        this.idCuenta = idCuenta;
        this.montoRetirado = montoRetirado;
        this.saldoFinal = saldoFinal;
    }

    public Integer getIdCuenta() { return idCuenta; }
    public Double getMontoRetirado() { return montoRetirado; }
    public Double getSaldoFinal() { return saldoFinal; }
}
