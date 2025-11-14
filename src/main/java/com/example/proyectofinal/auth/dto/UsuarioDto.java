package com.example.proyectofinal.auth.dto;
import com.example.proyectofinal.usuario.Rol;

public class UsuarioDto {
    private Integer idUsuario;
    private String nombre;
    private String correo;
    private Rol rol;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}