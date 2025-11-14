package com.example.proyectofinal.usuario.retiro.dto;

public class GenerarRetiroResponse {

    private String token;
    private Integer idCuenta;
    private Double monto;
    private Double saldoDisponible;

    public GenerarRetiroResponse(String token, Integer idCuenta, Double monto, Double saldoDisponible) {
        this.token = token;
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.saldoDisponible = saldoDisponible;
    }

    public String getToken() { return token; }
    public Integer getIdCuenta() { return idCuenta; }
    public Double getMonto() { return monto; }
    public Double getSaldoDisponible() { return saldoDisponible; }
}
