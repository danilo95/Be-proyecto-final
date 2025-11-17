package com.example.cajero.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo")
    private Integer idPrestamo;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Double interes;

    // CAMPO MODIFICADO (de anios a meses)
    @Column(nullable = false)
    private Integer plazoMeses; // CAMBIO: de plazoAnios a plazoMeses

    @Column(nullable = false)
    private Double cuotaMensual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "idCajero", nullable = false)
    private Usuario cajero;

    // CAMPOS NUEVOS AGREGADOS
    @Column(name = "fechaSolicitud")
    private LocalDateTime fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "idCuenta")
    private CuentaBancaria cuenta;

    // getters/setters
    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Double getInteres() { return interes; }
    public void setInteres(Double interes) { this.interes = interes; }

    // GETTER/SETTER MODIFICADO
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }

    public Double getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(Double cuotaMensual) { this.cuotaMensual = cuotaMensual; }

    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }

    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }

    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }

    // GETTERS/SETTERS NUEVOS
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public CuentaBancaria getCuenta() { return cuenta; }
    public void setCuenta(CuentaBancaria cuenta) { this.cuenta = cuenta; }
}