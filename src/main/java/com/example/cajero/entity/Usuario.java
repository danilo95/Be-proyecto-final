package com.example.cajero.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String dui;

    private String direccion;
    private String telefono;

    @Column(unique = true)
    private String correo;

    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    private Double salario;

    // CAMPOS NUEVOS AGREGADOS
    @Column(name = "activo")
    private Boolean activo = true;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<CuentaBancaria> cuentas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Prestamo> prestamos = new ArrayList<>();

    // getters/setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

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

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    // GETTERS/SETTERS NUEVOS
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<CuentaBancaria> getCuentas() { return cuentas; }
    public void setCuentas(List<CuentaBancaria> cuentas) { this.cuentas = cuentas; }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }
}