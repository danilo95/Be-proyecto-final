package com.example.cajero.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CrearDependienteRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    @Size(min = 8, max = 20)
    private String dui;

    private String telefono;

    @Email
    private String correo;

    @NotBlank
    private String contrasena;

    @NotBlank
    private String nombreComercio;

    // getters/setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreComercio() { return nombreComercio; }
    public void setNombreComercio(String nombreComercio) { this.nombreComercio = nombreComercio; }
}