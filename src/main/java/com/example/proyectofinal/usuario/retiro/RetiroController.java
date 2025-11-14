package com.example.proyectofinal.usuario.retiro;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.retiro.dto.ConfirmarRetiroRequest;
import com.example.proyectofinal.usuario.retiro.dto.ConfirmarRetiroResponse;
import com.example.proyectofinal.usuario.retiro.dto.GenerarRetiroRequest;
import com.example.proyectofinal.usuario.retiro.dto.GenerarRetiroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class RetiroController {

    private final RetiroService retiroService;
    private final JwtService jwtService;

    public RetiroController(RetiroService retiroService, JwtService jwtService) {
        this.retiroService = retiroService;
        this.jwtService = jwtService;
    }

    // 1) Generar retiro: POST /usuarios/{idUsuario}/retiros
    @PostMapping("/usuarios/{idUsuario}/retiros")
    public ResponseEntity<?> generarRetiro(
            @PathVariable Integer idUsuario,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody GenerarRetiroRequest request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token no proporcionado");
            }

            String tokenJwt = authorizationHeader.substring(7);
            Integer idDelToken = jwtService.getUserIdFromToken(tokenJwt);
            String rolDelToken = jwtService.getRolFromToken(tokenJwt);

            // sólo el dueño o Admin pueden generar el retiro
            if (!idUsuario.equals(idDelToken) && !"Admin".equals(rolDelToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para generar retiros de este usuario");
            }

            GenerarRetiroResponse response =
                    retiroService.generarRetiro(idUsuario, request.getMonto());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // 2) Confirmar retiro: POST /retiros/confirmar
    @PostMapping("/retiros/confirmar")
    public ResponseEntity<?> confirmarRetiro(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody ConfirmarRetiroRequest request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token JWT no proporcionado");
            }

            // aquí solo validamos que el JWT sea válido
            String tokenJwt = authorizationHeader.substring(7);
            jwtService.getUserIdFromToken(tokenJwt); // si es inválido, lanzará excepción

            ConfirmarRetiroResponse response =
                    retiroService.confirmarRetiro(request.getToken());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
