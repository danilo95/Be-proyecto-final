package com.example.proyectofinal.usuario.dependiente.dto;

public class DependienteDto {

    private Integer idDependiente;
    private String nombre;
    private String nombreComercio;
    private Double porcentajeComision;

    public Integer getIdDependiente() { return idDependiente; }
    public void setIdDependiente(Integer idDependiente) { this.idDependiente = idDependiente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreComercio() { return nombreComercio; }
    public void setNombreComercio(String nombreComercio) { this.nombreComercio = nombreComercio; }

    public Double getPorcentajeComision() { return porcentajeComision; }
    public void setPorcentajeComision(Double porcentajeComision) { this.porcentajeComision = porcentajeComision; }
}
