package com.example.proyectofinal.usuario.cuenta;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.cuenta.dto.CuentaDto;
import com.example.proyectofinal.usuario.movimiento.MovimientoService;
import com.example.proyectofinal.usuario.movimiento.dto.MovimientoDto;
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

    private final MovimientoService movimientoService;

    public CuentaController(
            CuentaService cuentaService,
            JwtService jwtService,
            MovimientoService movimientoService
    ) {
        this.cuentaService = cuentaService;
        this.jwtService = jwtService;
        this.movimientoService = movimientoService;
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

    @GetMapping("/{idUsuario}/cuentas/{idCuenta}/movimientos")
    public ResponseEntity<?> obtenerMovimientosCuenta(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idCuenta,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token no proporcionado");
        }

        String token = authorizationHeader.substring(7);

        Integer idDelToken = jwtService.getUserIdFromToken(token);
        String rolDelToken = jwtService.getRolFromToken(token);

        // ✔️ Dueño o Admin
        if (!idUsuario.equals(idDelToken) && !"Admin".equals(rolDelToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para ver estos movimientos");
        }

        // Validar que la cuenta realmente pertenece a ese usuario
        var cuenta = cuentaService.obtenerCuentaPorId(idCuenta);
        if (!cuenta.getCliente().getIdUsuario().equals(idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("La cuenta no pertenece a este usuario");
        }

        List<MovimientoDto> movimientos = movimientoService.obtenerMovimientosPorCuenta(idCuenta);
        return ResponseEntity.ok(movimientos);
    }
}
