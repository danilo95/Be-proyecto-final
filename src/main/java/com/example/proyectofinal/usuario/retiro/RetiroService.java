package com.example.proyectofinal.usuario.retiro;

import com.example.proyectofinal.usuario.Usuario;
import com.example.proyectofinal.usuario.UsuarioRepository;
import com.example.proyectofinal.usuario.cuenta.CuentaBancaria;
import com.example.proyectofinal.usuario.cuenta.CuentaBancariaRepository;
import com.example.proyectofinal.usuario.movimiento.Movimiento;
import com.example.proyectofinal.usuario.movimiento.MovimientoRepository;
import com.example.proyectofinal.usuario.movimiento.TipoMovimiento;
import com.example.proyectofinal.usuario.retiro.dto.ConfirmarRetiroResponse;
import com.example.proyectofinal.usuario.retiro.dto.GenerarRetiroResponse;
import com.example.proyectofinal.usuario.token.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RetiroService {

    private final UsuarioRepository usuarioRepository;
    private final CuentaBancariaRepository cuentaRepo;
    private final TokenTransaccionRepository tokenRepo;
    private final MovimientoRepository movimientoRepo;

    public RetiroService(UsuarioRepository usuarioRepository,
                         CuentaBancariaRepository cuentaRepo,
                         TokenTransaccionRepository tokenRepo,
                         MovimientoRepository movimientoRepo) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepo = cuentaRepo;
        this.tokenRepo = tokenRepo;
        this.movimientoRepo = movimientoRepo;
    }

    public GenerarRetiroResponse generarRetiro(Integer idUsuario, Double monto) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CuentaBancaria cuenta = cuentaRepo
                .findFirstByCliente_IdUsuarioOrderByIdCuentaAsc(idUsuario)
                .orElseThrow(() -> new RuntimeException("El usuario no tiene cuenta asignada"));

        Double saldo = cuenta.getSaldo() != null ? cuenta.getSaldo() : 0.0;

        if (monto > saldo) {
            throw new RuntimeException("Saldo insuficiente para generar el retiro");
        }

        String tokenStr = UUID.randomUUID().toString();

        TokenTransaccion token = new TokenTransaccion();
        token.setToken(tokenStr);
        token.setCuenta(cuenta);
        token.setUsuario(usuario);
        token.setTipo(TipoTokenTransaccion.Retiro);
        token.setMonto(monto);
        token.setEstado(EstadoToken.Pendiente);
        token.setFechaCreacion(LocalDateTime.now());

        tokenRepo.save(token);

        return new GenerarRetiroResponse(tokenStr, cuenta.getIdCuenta(), monto, saldo);
    }

    @Transactional
    public ConfirmarRetiroResponse confirmarRetiro(String tokenStr) {

        TokenTransaccion token = tokenRepo.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Token no válido"));

        if (token.getTipo() != TipoTokenTransaccion.Retiro) {
            throw new RuntimeException("El token no corresponde a un retiro");
        }

        if (token.getEstado() != EstadoToken.Pendiente) {
            throw new RuntimeException("El token ya fue utilizado o expiró");
        }

        CuentaBancaria cuenta = token.getCuenta();
        Double saldo = cuenta.getSaldo() != null ? cuenta.getSaldo() : 0.0;
        Double monto = token.getMonto();

        if (monto > saldo) {
            throw new RuntimeException("Saldo insuficiente al momento de confirmar el retiro");
        }

        // Debitar saldo
        cuenta.setSaldo(saldo - monto);
        cuentaRepo.save(cuenta);

        // Marcar token como usado
        token.setEstado(EstadoToken.Usado);
        token.setFechaUso(LocalDateTime.now());
        tokenRepo.save(token);

        // Registrar movimiento
        Movimiento mov = new Movimiento();
        mov.setCuenta(cuenta);
        mov.setUsuario(token.getUsuario());
        mov.setTipo(TipoMovimiento.Retiro);
        mov.setMonto(monto);
        mov.setFecha(LocalDateTime.now());
        movimientoRepo.save(mov);

        return new ConfirmarRetiroResponse(cuenta.getIdCuenta(), monto, cuenta.getSaldo());
    }
}
