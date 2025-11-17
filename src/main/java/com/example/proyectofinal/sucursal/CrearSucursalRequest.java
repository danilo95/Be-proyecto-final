package com.example.proyectofinal.sucursal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CrearSucursalRequest {
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 200)
    private String direccion;

    private Integer idGerenteSucursal;

    // getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getIdGerenteSucursal() { return idGerenteSucursal; }
    public void setIdGerenteSucursal(Integer idGerenteSucursal) { this.idGerenteSucursal = idGerenteSucursal; }
}
