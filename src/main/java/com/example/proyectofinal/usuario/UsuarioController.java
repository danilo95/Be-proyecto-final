package com.example.proyectofinal.usuario;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.dto.UsuarioListaDto;
import com.example.proyectofinal.usuario.dto.UsuarioDetalleDto;
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


    @GetMapping("/por-dui/{dui}")
    public ResponseEntity<?> obtenerPorDui(
            @PathVariable String dui,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token no proporcionado");
        }

        String token = authorizationHeader.substring(7);
        Integer idDelToken = jwtService.getUserIdFromToken(token);
        String rolDelToken = jwtService.getRolFromToken(token);


        if ("Cliente".equals(rolDelToken)) {

            UsuarioDetalleDto detallePropio =
                    usuarioService.obtenerUsuarioPorDuiConCuentasYPrestamos(dui);

            if (!detallePropio.getIdUsuario().equals(idDelToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No puedes consultar datos de otro cliente");
            }

            return ResponseEntity.ok(detallePropio);
        }

        // aca esta para que puedan ver otros roles el listado por dui
        UsuarioDetalleDto detalle =
                usuarioService.obtenerUsuarioPorDuiConCuentasYPrestamos(dui);

        return ResponseEntity.ok(detalle);
    }
}
