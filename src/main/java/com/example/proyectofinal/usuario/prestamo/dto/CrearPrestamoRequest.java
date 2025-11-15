package com.example.proyectofinal.usuario.prestamo.dto;

public class CrearPrestamoRequest {

    private Integer idCliente;
    private Double monto;
    private Double interes;
    private Integer plazoAnios;
    private Double cuotaMensual;

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Double getInteres() { return interes; }
    public void setInteres(Double interes) { this.interes = interes; }

    public Integer getPlazoAnios() { return plazoAnios; }
    public void setPlazoAnios(Integer plazoAnios) { this.plazoAnios = plazoAnios; }

    public Double getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(Double cuotaMensual) { this.cuotaMensual = cuotaMensual; }
}
