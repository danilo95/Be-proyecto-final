package com.example.cajero.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cajero.dto.CrearClienteRequest;
import com.example.cajero.dto.CrearCuentaRequest;
import com.example.cajero.dto.CrearDependienteRequest;
import com.example.cajero.dto.MovimientoRequest;
import com.example.cajero.dto.PrestamoRequest;
import com.example.cajero.dto.TransaccionRequest;
import com.example.cajero.entity.CuentaBancaria;
import com.example.cajero.entity.Movimiento;
import com.example.cajero.entity.Prestamo;
import com.example.cajero.entity.Usuario;
import com.example.cajero.service.CuentaService;
import com.example.cajero.service.MovimientoService;
import com.example.cajero.service.PrestamoService;
import com.example.cajero.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cajero")
@CrossOrigin(origins = "*")
public class CajeroController {

    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;
    private final PrestamoService prestamoService;

    public CajeroController(UsuarioService usuarioService,
                            CuentaService cuentaService,
                            MovimientoService movimientoService,
                            PrestamoService prestamoService) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
        this.prestamoService = prestamoService;
    }

    // 1. CREAR NUEVO CLIENTE
    @PostMapping("/clientes")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody CrearClienteRequest req) {
        try {
            Usuario cliente = usuarioService.crearClienteDesdeRequest(req);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 2. CREAR DEPENDIENTE (NUEVO)
    @PostMapping("/dependientes")
    public ResponseEntity<?> crearDependiente(@Valid @RequestBody CrearDependienteRequest req) {
        try {
            Usuario dependiente = usuarioService.crearDependiente(req);
            return ResponseEntity.ok(dependiente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 3. CREAR CUENTA PARA CLIENTE (NUEVO)
    @PostMapping("/cuentas")
    public ResponseEntity<?> crearCuenta(@Valid @RequestBody CrearCuentaRequest req) {
        try {
            // Validar máximo de cuentas
            if (usuarioService.validarMaximoCuentas(req.getIdCliente())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Máximo 3 cuentas por cliente"));
            }

            CuentaBancaria cuenta = cuentaService.crearCuentaParaCliente(req.getIdCliente(), req.getTipoCuenta());
            return ResponseEntity.ok(cuenta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 4. REALIZAR TRANSACCIÓN (DEPÓSITO O RETIRO) - ACTUALIZADO
    @PostMapping("/transacciones")
    public ResponseEntity<?> realizarTransaccion(@Valid @RequestBody TransaccionRequest req) {
        try {
            Movimiento movimiento = cuentaService.realizarTransaccion(req);
            return ResponseEntity.ok(movimiento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 5. SOLICITAR PRÉSTAMO - ACTUALIZADO
    @PostMapping("/prestamos")
    public ResponseEntity<?> crearPrestamo(@Valid @RequestBody PrestamoRequest req) {
        try {
            Prestamo prestamo = prestamoService.crearPrestamo(req);
            return ResponseEntity.ok(prestamo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 6. BUSCAR CLIENTE POR DUI (NUEVO)
    @GetMapping("/clientes/buscar")
    public ResponseEntity<?> buscarClientePorDui(@RequestParam String dui) {
        try {
            Usuario cliente = usuarioService.buscarClientePorDui(dui);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 7. OBTENER CUENTAS POR DUI (NUEVO)
    @GetMapping("/clientes/{dui}/cuentas")
    public ResponseEntity<?> obtenerCuentasPorDui(@PathVariable String dui) {
        try {
            List<CuentaBancaria> cuentas = cuentaService.obtenerCuentasPorDui(dui);
            return ResponseEntity.ok(cuentas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 8. OBTENER MOVIMIENTOS POR CUENTA (NUEVO)
    @GetMapping("/cuentas/{numeroCuenta}/movimientos")
    public ResponseEntity<?> obtenerMovimientosPorCuenta(@PathVariable String numeroCuenta) {
        try {
            List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
            return ResponseEntity.ok(movimientos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 9. OBTENER PRÉSTAMOS POR CLIENTE (NUEVO)
    @GetMapping("/clientes/{idCliente}/prestamos")
    public ResponseEntity<?> obtenerPrestamosPorCliente(@PathVariable Integer idCliente) {
        try {
            List<Prestamo> prestamos = prestamoService.obtenerPrestamosPorCliente(idCliente);
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 10. OBTENER PRÉSTAMOS EN ESPERA (NUEVO)
    @GetMapping("/prestamos/en-espera")
    public ResponseEntity<?> obtenerPrestamosEnEspera() {
        try {
            List<Prestamo> prestamos = prestamoService.obtenerPrestamosEnEspera();
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 11. VALIDAR CUENTA POR DUI (NUEVO)
    @GetMapping("/cuentas/validar")
    public ResponseEntity<?> validarCuentaPorDui(@RequestParam String numeroCuenta, @RequestParam String dui) {
        try {
            boolean esValida = cuentaService.validarCuentaPorDui(numeroCuenta, dui);
            return ResponseEntity.ok(Map.of("valido", esValida));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 12. OBTENER TODOS LOS CLIENTES ACTIVOS (NUEVO)
    @GetMapping("/clientes")
    public ResponseEntity<?> obtenerClientesActivos() {
        try {
            List<Usuario> clientes = usuarioService.obtenerClientesActivos();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}