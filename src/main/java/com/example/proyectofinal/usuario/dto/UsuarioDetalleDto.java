package com.example.proyectofinal.usuario.dto;


import com.example.proyectofinal.usuario.Rol;
import com.example.proyectofinal.usuario.cuenta.dto.CuentaDto;
import com.example.proyectofinal.usuario.prestamo.dto.PrestamoDto;

import java.util.List;

public class UsuarioDetalleDto {

    private Integer idUsuario;
    private String nombre;
    private String dui;
    private String correo;
    private Rol rol;

    private List<CuentaDto> cuentas;
    private List<PrestamoDto> prestamos;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public List<CuentaDto> getCuentas() { return cuentas; }
    public void setCuentas(List<CuentaDto> cuentas) { this.cuentas = cuentas; }

    public List<PrestamoDto> getPrestamos() { return prestamos; }
    public void setPrestamos(List<PrestamoDto> prestamos) { this.prestamos = prestamos; }
}
