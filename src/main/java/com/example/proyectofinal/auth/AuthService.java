package com.example.proyectofinal.auth;

import com.example.proyectofinal.auth.dto.LoginRequest;
import com.example.proyectofinal.auth.dto.LoginResponse;
import com.example.proyectofinal.auth.dto.UsuarioDto;
import com.example.proyectofinal.usuario.Usuario;
import com.example.proyectofinal.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        // Buscar usuario por correo
        Usuario usuario = usuarioRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));

        // Comparar contraseña (texto plano por ahora)
        if (!usuario.getContrasena().equals(request.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }

        String token = jwtService.generateToken(usuario);

        UsuarioDto dto = new UsuarioDto();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());

        return new LoginResponse(token, dto);
    }
}
