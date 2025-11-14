package com.example.proyectofinal.usuario.cuenta;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.cuenta.dto.CuentaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class CuentaController {

    private final CuentaService cuentaService;
    private final JwtService jwtService;

    public CuentaController(CuentaService cuentaService, JwtService jwtService) {
        this.cuentaService = cuentaService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{idUsuario}/cuentas")
    public ResponseEntity<?> obtenerCuentasUsuario(
            @PathVariable Integer idUsuario,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado");
        }

        String token = authorizationHeader.substring(7);

        Integer idDelToken = jwtService.getUserIdFromToken(token);
        String rolDelToken = jwtService.getRolFromToken(token);

        // && !"Admin".equals(rolDelToken) para validar permisos
        if (!idUsuario.equals(idDelToken) && !"Admin".equals(rolDelToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para ver estas cuentas");
        }

        List<CuentaDto> cuentas = cuentaService.obtenerCuentasPorUsuario(idUsuario);
        return ResponseEntity.ok(cuentas);
    }
}
