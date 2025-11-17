package com.example.proyectofinal.usuario.empleado;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Sucursal")
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSucursal")
    private Integer idSucursal;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "direccion", length = 200)
    private String direccion;

    /**
     * id del gerente tal como aparece en la tabla (FK hacia Usuario/Empleado).
     * Lo modelamos como Integer para evitar acoplar la entidad a la clase Usuario.
     * Si prefieres una relación @ManyToOne, indícamelo y adapto el mapeo.
     */
    @Column(name = "idGerenteSucursal")
    private Integer idGerenteSucursal;

    public Sucursal() {}

    // Constructor de conveniencia
    public Sucursal(Integer idSucursal, String nombre, String direccion, Integer idGerenteSucursal) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.idGerenteSucursal = idGerenteSucursal;
    }

    // Getters / Setters
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getIdGerenteSucursal() { return idGerenteSucursal; }
    public void setIdGerenteSucursal(Integer idGerenteSucursal) { this.idGerenteSucursal = idGerenteSucursal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sucursal sucursal = (Sucursal) o;
        return Objects.equals(idSucursal, sucursal.idSucursal);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idSucursal);
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "idSucursal=" + idSucursal +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", idGerenteSucursal=" + idGerenteSucursal +
                '}';
    }
}
