package com.example.proyectofinal.usuario.cuenta;

import com.example.proyectofinal.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "CuentaBancaria")
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCuenta")
    private Integer idCuenta;

    @Column(name = "numeroCuenta")
    private String numeroCuenta;

    @Column(name = "saldo")
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "idCliente") // FK a Usuario.idUsuario
    private Usuario cliente;

    public Integer getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Integer idCuenta) { this.idCuenta = idCuenta; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }
}