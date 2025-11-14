package com.example.proyectofinal.usuario;
import jakarta.persistence.*;


    @Entity
    @Table(name = "Usuario")
    public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idUsuario")
        private Integer idUsuario;

        private String nombre;

        private String dui;

        private String direccion;

        private String telefono;

        private String correo;

        @Column(name = "contraseña")
        private String contrasena;

        @Enumerated(EnumType.STRING)
        private Rol rol;

        private Double salario;

        // getters y setters
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
    }

