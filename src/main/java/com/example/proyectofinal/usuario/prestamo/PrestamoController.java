package com.example.proyectofinal.usuario.prestamo;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.prestamo.dto.CrearPrestamoRequest;
import com.example.proyectofinal.usuario.prestamo.dto.PrestamoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final JwtService jwtService;

    public PrestamoController(PrestamoService prestamoService, JwtService jwtService) {
        this.prestamoService = prestamoService;
        this.jwtService = jwtService;
    }

    private boolean rolPuedeCrearPrestamo(String rol) {
        return "GerenteSucursal".equals(rol)
                || "GerenteGeneral".equals(rol)
                || "Admin".equals(rol);
    }

   
    @PostMapping
    public ResponseEntity<?> crearPrestamo(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody CrearPrestamoRequest request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token no proporcionado");
            }

            String tokenJwt = authorizationHeader.substring(7);
            String rolDelToken = jwtService.getRolFromToken(tokenJwt);
            Integer idUsuarioDelToken = jwtService.getUserIdFromToken(tokenJwt);

            // Solo GerenteSucursal, GerenteGeneral y Admin pueden crear préstamos
            if (!rolPuedeCrearPrestamo(rolDelToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para crear préstamos");
            }

            PrestamoDto prestamo = prestamoService.crearPrestamo(request, idUsuarioDelToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
