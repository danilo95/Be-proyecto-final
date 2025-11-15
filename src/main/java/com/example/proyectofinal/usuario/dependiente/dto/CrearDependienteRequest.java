package com.example.proyectofinal.usuario.dependiente.dto;

public class CrearDependienteRequest {

    // id del Usuario que será dependiente
    private Integer idUsuario;
    private String nombreComercio;
    private Double porcentajeComision;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreComercio() { return nombreComercio; }
    public void setNombreComercio(String nombreComercio) { this.nombreComercio = nombreComercio; }

    public Double getPorcentajeComision() { return porcentajeComision; }
    public void setPorcentajeComision(Double porcentajeComision) { this.porcentajeComision = porcentajeComision; }
}
