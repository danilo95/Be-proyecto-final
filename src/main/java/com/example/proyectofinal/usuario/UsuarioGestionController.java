package com.example.proyectofinal.usuario;

import com.example.proyectofinal.auth.JwtService;
import com.example.proyectofinal.usuario.dependiente.DependienteService;
import com.example.proyectofinal.usuario.dependiente.dto.CrearDependienteRequest;
import com.example.proyectofinal.usuario.dependiente.dto.DependienteDto;
import com.example.proyectofinal.usuario.dto.CrearUsuarioRequest;
import com.example.proyectofinal.usuario.dto.UsuarioListaDto;
import com.example.proyectofinal.usuario.empleado.EmpleadoService;
import com.example.proyectofinal.usuario.empleado.dto.CrearEmpleadoRequest;
import com.example.proyectofinal.usuario.empleado.dto.EmpleadoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioGestionController {

    private final JwtService jwtService;
    private final DependienteService dependienteService;
    private final EmpleadoService empleadoService;
    private final UsuarioService usuarioService;

    public UsuarioGestionController(
            JwtService jwtService,
            DependienteService dependienteService,
            EmpleadoService empleadoService,
            UsuarioService usuarioService
    ) {
        this.jwtService = jwtService;
        this.dependienteService = dependienteService;
        this.empleadoService = empleadoService;
        this.usuarioService = usuarioService;
    }

    private boolean rolPuedeCrear(String rol) {
        return "Cajero".equals(rol)
                || "GerenteSucursal".equals(rol)
                || "GerenteGeneral".equals(rol)
                || "Admin".equals(rol);
    }

    @PostMapping("/dependientes")
    public ResponseEntity<?> crearDependiente(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody CrearDependienteRequest request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token no proporcionado");
            }

            String token = authorizationHeader.substring(7);
            String rol = jwtService.getRolFromToken(token);

            if (!rolPuedeCrear(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para crear dependientes");
            }

            DependienteDto dto = dependienteService.crearDependiente(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody CrearEmpleadoRequest request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token no proporcionado");
            }

            String token = authorizationHeader.substring(7);
            String rol = jwtService.getRolFromToken(token);

            if (!rolPuedeCrear(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para crear empleados");
            }

            EmpleadoDto dto = empleadoService.crearEmpleado(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuarioConRol(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody CrearUsuarioRequest request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token no proporcionado");
            }

            String token = authorizationHeader.substring(7);
            String rolDelToken = jwtService.getRolFromToken(token);

            // Cliente NO puede crear usuarios
            if ("Cliente".equals(rolDelToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("El rol Cliente no puede crear usuarios");
            }

            // Regla especial: solo Admin puede crear otro Admin
            if (request.getRol() == Rol.Admin && !"Admin".equals(rolDelToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Solo un Admin puede crear otro usuario Admin");
            }

            UsuarioListaDto creado = usuarioService.crearUsuarioConRol(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
