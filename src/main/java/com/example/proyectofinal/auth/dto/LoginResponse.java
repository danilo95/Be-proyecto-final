package com.example.proyectofinal.auth.dto;

import com.example.proyectofinal.auth.dto.UsuarioDto;

public class LoginResponse {
    private String token;
    private UsuarioDto usuario;

    public LoginResponse(String token, UsuarioDto usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() { return token; }
    public UsuarioDto getUsuario() { return usuario; }
}