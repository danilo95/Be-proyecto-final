package com.example.proyectofinal.usuario.empleado;

import com.example.proyectofinal.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Empleado")
public class Empleado {

    @Id
    @Column(name = "idEmpleado")
    private Integer idEmpleado;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idEmpleado")
    private Usuario usuario;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "estado")
    private String estado; // Activo / Inactivo / Espera

    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;

    public Integer getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
}
