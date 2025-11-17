package com.example.cajero.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cajero.dto.TransaccionRequest;
import com.example.cajero.entity.CuentaBancaria;
import com.example.cajero.entity.Movimiento;
import com.example.cajero.entity.TipoMovimiento;
import com.example.cajero.entity.Usuario;
import com.example.cajero.exception.BadRequestException;
import com.example.cajero.exception.NotFoundException;
import com.example.cajero.repository.CuentaRepository;
import com.example.cajero.repository.MovimientoRepository;
import com.example.cajero.repository.UsuarioRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepo;
    private final UsuarioRepository usuarioRepo;
    private final MovimientoRepository movimientoRepo;

    public CuentaService(CuentaRepository cuentaRepo, UsuarioRepository usuarioRepo, MovimientoRepository movimientoRepo) {
        this.cuentaRepo = cuentaRepo;
        this.usuarioRepo = usuarioRepo;
        this.movimientoRepo = movimientoRepo;
    }

    @Transactional
    public CuentaBancaria crearCuentaParaCliente(Integer idCliente, String tipoCuenta) {
        Usuario cliente = usuarioRepo.findById(idCliente)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        // Validar máximo de cuentas (usando el nombre correcto)
        if (cuentaRepo.countByCliente_IdUsuario(idCliente) >= 3) {
            throw new BadRequestException("Máximo 3 cuentas por cliente");
        }

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setCliente(cliente);
        cuenta.setNumeroCuenta(generarNumeroCuenta());
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setSaldo(0.0);
        cuenta.setFechaCreacion(LocalDateTime.now());

        return cuentaRepo.save(cuenta);
    }

    private String generarNumeroCuenta() {
        Random random = new Random();
        String numero;
        do {
            numero = String.format("%010d", random.nextInt(1000000000));
        } while (cuentaRepo.existsByNumeroCuenta(numero));
        return numero;
    }

    @Transactional
    public Movimiento realizarTransaccion(TransaccionRequest request) {
        CuentaBancaria cuenta = cuentaRepo.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        if (!cuenta.getCliente().getDui().equals(request.getDui())) {
            throw new BadRequestException("El DUI no corresponde al titular de la cuenta");
        }

        if ("DEPOSITO".equalsIgnoreCase(request.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo() + request.getMonto());
        } else if ("RETIRO".equalsIgnoreCase(request.getTipo())) {
            if (cuenta.getSaldo() < request.getMonto()) {
                throw new BadRequestException("Saldo insuficiente");
            }
            cuenta.setSaldo(cuenta.getSaldo() - request.getMonto());
        } else {
            throw new BadRequestException("Tipo de transacción no válido");
        }

        cuentaRepo.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setMonto(request.getMonto());
        movimiento.setTipo(TipoMovimiento.valueOf(request.getTipo().toUpperCase()));
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setDescripcion(request.getTipo() + " realizado por cajero");

        return movimientoRepo.save(movimiento);
    }

    public List<CuentaBancaria> obtenerCuentasPorDui(String dui) {
        Usuario cliente = usuarioRepo.findByDui(dui)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con DUI: " + dui));

        if (cliente.getRol() != com.example.cajero.entity.Rol.Cliente) {
            throw new BadRequestException("El DUI no pertenece a un cliente");
        }

        return cuentaRepo.findByCliente_IdUsuario(cliente.getIdUsuario());
    }

    public boolean validarCuentaPorDui(String numeroCuenta, String dui) {
        CuentaBancaria cuenta = cuentaRepo.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        return cuenta.getCliente().getDui().equals(dui);
    }

    public CuentaBancaria obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepo.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
    }

    public List<CuentaBancaria> obtenerCuentasDeCliente(Integer idCliente) {
        return cuentaRepo.findByCliente_IdUsuario(idCliente);
    }
}