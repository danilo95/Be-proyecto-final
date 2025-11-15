package com.example.proyectofinal.usuario.dependiente;

import com.example.proyectofinal.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Dependiente")
public class Dependiente {

    @Id
    @Column(name = "idDependiente")
    private Integer idDependiente;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idDependiente")
    private Usuario usuario;

    @Column(name = "nombreComercio", nullable = false)
    private String nombreComercio;

    @Column(name = "porcentajeComision", nullable = false)
    private Double porcentajeComision;

    public Integer getIdDependiente() { return idDependiente; }
    public void setIdDependiente(Integer idDependiente) { this.idDependiente = idDependiente; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getNombreComercio() { return nombreComercio; }
    public void setNombreComercio(String nombreComercio) { this.nombreComercio = nombreComercio; }

    public Double getPorcentajeComision() { return porcentajeComision; }
    public void setPorcentajeComision(Double porcentajeComision) { this.porcentajeComision = porcentajeComision; }
}
