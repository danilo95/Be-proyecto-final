package com.example.cajero.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearClienteRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    @Size(min = 8, max = 20)
    private String dui;

    private String direccion;
    private String telefono;

    @Email
    private String correo;

    @NotBlank
    private String contrasena;

    // CAMPO NUEVO AGREGADO
    @NotNull
    private Double salario;

    // getters/setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // GETTER/SETTER NUEVO
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
}