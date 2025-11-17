package com.example.proyectofinal.usuario.empleado.dto;

public class EstadoUpdateDto {
    private String estado;

    public EstadoUpdateDto() {}

    public EstadoUpdateDto(String estado) { this.estado = estado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
