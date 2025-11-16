package com.example.proyectofinal.usuario.dto;

import com.example.proyectofinal.usuario.Rol;

public class UsuarioListaDto {

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private Rol rol;

    // NUEVOS CAMPOS
    private String dui;
    private String telefono;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
