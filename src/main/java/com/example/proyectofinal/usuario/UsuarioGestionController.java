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
            UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.dependienteService = dependienteService;
        this.empleadoService = empleadoService;
        this.usuarioService = usuarioService;
    }

    // -------------------
    // Crear empleado (único método)
    // -------------------
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

            // validación si el rol puede crear (mantén la lógica específica en rolPuedeCrear)
            if (!rolPuedeCrear(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para crear empleados");
            }

            Integer idGerente = jwtService.getUserIdFromToken(token);
            EmpleadoDto dto = empleadoService.crearEmpleado(request, idGerente);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // -------------------
    // Actualizar estado (solo GerenteGeneral)
    // -------------------
    @PutMapping("/empleados/{idEmpleado}/estado")
    public ResponseEntity<?> actualizarEstadoEmpleado(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable Integer idEmpleado,
            @RequestBody com.example.proyectofinal.usuario.empleado.dto.EstadoUpdateDto request
    ) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado");
            }
            String token = authorizationHeader.substring(7);
            String rol = jwtService.getRolFromToken(token);
            if (!"GerenteGeneral".equalsIgnoreCase(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo Gerente General puede actualizar estado");
            }
            Integer idGerente = jwtService.getUserIdFromToken(token);
            com.example.proyectofinal.usuario.empleado.dto.EmpleadoDto dto =
                    empleadoService.actualizarEstadoEmpleado(idGerente, idEmpleado, request.getEstado());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // -------------------
    // Helper para decidir qué roles pueden crear usuarios/empleados
    // -------------------
    private boolean rolPuedeCrear(String rol) {
        // Normalizamos case para evitar problemas
        if (rol == null) return false;
        String r = rol.trim().toLowerCase();
        return r.equals("cajero".toLowerCase())
                || r.equals("gerentesucursal".toLowerCase())
                || r.equals("gerentegeneral".toLowerCase())
                || r.equals("admin".toLowerCase());
    }

    // -------------------
    // Crear dependiente (se mantiene igual que antes)
    // -------------------
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

    // -------------------
    // Crear usuario con rol (se mantiene)
    // -------------------
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
            if ("Cliente".equalsIgnoreCase(rolDelToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("El rol Cliente no puede crear usuarios");
            }

            // Regla especial: solo Admin puede crear otro Admin
            if (request.getRol() == Rol.Admin && !"Admin".equalsIgnoreCase(rolDelToken)) {
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
