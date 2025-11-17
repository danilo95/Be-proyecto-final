package com.example.proyectofinal.sucursal.dto;

public class SucursalDto {
    private Integer idSucursal;
    private String nombre;
    private String direccion;
    private Integer idGerenteSucursal;

    // getters / setters
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getIdGerenteSucursal() { return idGerenteSucursal; }
    public void setIdGerenteSucursal(Integer idGerenteSucursal) { this.idGerenteSucursal = idGerenteSucursal; }
}
