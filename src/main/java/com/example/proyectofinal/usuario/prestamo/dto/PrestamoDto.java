package com.example.proyectofinal.usuario.prestamo.dto;

public class PrestamoDto {

    private Integer idPrestamo;
    private Double monto;
    private Double interes;
    private Integer plazoAnios;
    private Double cuotaMensual;
    private String estado;

    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Double getInteres() { return interes; }
    public void setInteres(Double interes) { this.interes = interes; }

    public Integer getPlazoAnios() { return plazoAnios; }
    public void setPlazoAnios(Integer plazoAnios) { this.plazoAnios = plazoAnios; }

    public Double getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(Double cuotaMensual) { this.cuotaMensual = cuotaMensual; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
