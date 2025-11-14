package com.example.proyectofinal.usuario;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.dto.UsuarioListaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }


    @GetMapping
    public ResponseEntity<?> listarUsuarios(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token no proporcionado");
        }

        String token = authorizationHeader.substring(7);

        String rolDelToken = jwtService.getRolFromToken(token);

        // 1) Bloquear a Cliente
        if ("Cliente".equals(rolDelToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El rol Cliente no puede ver la lista de usuarios");
        }

        // 2) Decidir si incluimos admins (revisar)
        boolean incluirAdmins = "Admin".equals(rolDelToken);

        List<UsuarioListaDto> usuarios = usuarioService.obtenerUsuariosVisibles(incluirAdmins);

        return ResponseEntity.ok(usuarios);
    }
}
