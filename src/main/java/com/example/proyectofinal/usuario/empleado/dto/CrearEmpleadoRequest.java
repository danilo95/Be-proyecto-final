package com.example.proyectofinal.usuario.empleado.dto;

public class CrearEmpleadoRequest {

    private Integer idUsuario;
    private String cargo;
    private String estado;   // "Activo", "Inactivo" o "Espera"
    private Integer idSucursal;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
}
