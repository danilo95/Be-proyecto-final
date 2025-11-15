package com.example.proyectofinal.usuario.prestamo;

import com.example.proyectofinal.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo")
    private Integer idPrestamo;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "interes")
    private Double interes;

    @Column(name = "plazoAnios")
    private Integer plazoAnios;

    @Column(name = "cuotaMensual")
    private Double cuotaMensual;

    @Column(name = "estado")
    private String estado; // para evitar pelear con espacios, lo dejamos String

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "idCajero")
    private Usuario cajero;

    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Double getInteres() { return interes; }
    public void setInteres(Double interes) { this.interes = interes; }

    public Integer getPlazoAnios() { return plazoAnios; }
    public void setPlazoAnios(Integer plazoAnios) { this.plazoAnios = plazoAnios; }

    public Double getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(Double cuotaMensual) { this.cuotaMensual = cuotaMensual; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }

    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }
}
