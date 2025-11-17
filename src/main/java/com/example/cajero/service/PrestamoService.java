package com.example.cajero.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cajero.dto.PrestamoRequest;
import com.example.cajero.entity.CuentaBancaria;
import com.example.cajero.entity.EstadoPrestamo;
import com.example.cajero.entity.Prestamo;
import com.example.cajero.entity.Rol;
import com.example.cajero.entity.Usuario;
import com.example.cajero.exception.BadRequestException;
import com.example.cajero.exception.NotFoundException;
import com.example.cajero.repository.CuentaRepository;
import com.example.cajero.repository.PrestamoRepository;
import com.example.cajero.repository.UsuarioRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepo;
    private final UsuarioRepository usuarioRepo;
    private final CuentaRepository cuentaRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, UsuarioRepository usuarioRepo, CuentaRepository cuentaRepo) {
        this.prestamoRepo = prestamoRepo;
        this.usuarioRepo = usuarioRepo;
        this.cuentaRepo = cuentaRepo;
    }

    @Transactional
    public Prestamo crearPrestamo(PrestamoRequest request) {
        Usuario cajero = usuarioRepo.findById(request.getIdCajero())
                .orElseThrow(() -> new NotFoundException("Cajero no encontrado"));

        if (cajero.getRol() != Rol.Cajero && cajero.getRol() != Rol.Admin) {
            throw new BadRequestException("Usuario no autorizado como cajero");
        }

        Usuario cliente = usuarioRepo.findById(request.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        CuentaBancaria cuenta = cuentaRepo.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        // Validar que la cuenta pertenece al cliente
        if (!cuenta.getCliente().getIdUsuario().equals(cliente.getIdUsuario())) {
            throw new BadRequestException("La cuenta no pertenece al cliente");
        }

        double salario = cliente.getSalario() != null ? cliente.getSalario() : 0.0;

        // Validaciones de préstamo
        validarPrestamo(cliente, request.getMonto());

        double interes = calcularInteresPorSalario(salario);
        int plazoMeses = calcularPlazoMeses(salario, request.getMonto(), interes);
        double cuotaMensual = calcularCuotaMensual(request.getMonto(), interes, plazoMeses);

        // Validar que la cuota no supere 30% del salario
        if (cuotaMensual > (salario * 0.3)) {
            throw new BadRequestException("La cuota mensual supera el 30% del salario del cliente");
        }

        Prestamo p = new Prestamo();
        p.setMonto(request.getMonto());
        p.setInteres(interes);
        p.setPlazoMeses(plazoMeses);
        p.setCuotaMensual(Math.round(cuotaMensual * 100.0) / 100.0);
        p.setEstado(EstadoPrestamo.En_espera);
        p.setCliente(cliente);
        p.setCajero(cajero);
        p.setCuenta(cuenta);
        p.setFechaSolicitud(LocalDateTime.now());

        return prestamoRepo.save(p);
    }

    private void validarPrestamo(Usuario cliente, Double monto) {
        Double salario = cliente.getSalario();

        if (salario == null || salario <= 0) {
            throw new BadRequestException("El cliente debe tener un salario válido");
        }

        if (salario <= 365 && monto > 10000) {
            throw new BadRequestException("Monto máximo para salario <= $365 es $10,000");
        } else if (salario <= 600 && monto > 25000) {
            throw new BadRequestException("Monto máximo para salario <= $600 es $25,000");
        } else if (salario <= 900 && monto > 35000) {
            throw new BadRequestException("Monto máximo para salario <= $900 es $35,000");
        } else if (salario > 1000 && monto > 50000) {
            throw new BadRequestException("Monto máximo para salario > $1,000 es $50,000");
        }
    }

    private int calcularPlazoMeses(double salario, double monto, double interes) {
        double cuotaMaxima = salario * 0.3;
        double tasaMensual = interes / 100 / 12;

        if (tasaMensual == 0) {
            return (int) Math.ceil(monto / cuotaMaxima);
        }

        int plazo = (int) Math.ceil(-Math.log(1 - (monto * tasaMensual) / cuotaMaxima) / Math.log(1 + tasaMensual));

        return Math.min(plazo, 360);
    }

    private double calcularCuotaMensual(double monto, double interes, int plazoMeses) {
        double tasaMensual = interes / 100 / 12;

        if (tasaMensual == 0) {
            return monto / plazoMeses;
        }

        return (monto * tasaMensual * Math.pow(1 + tasaMensual, plazoMeses)) /
                (Math.pow(1 + tasaMensual, plazoMeses) - 1);
    }

    private double calcularInteresPorSalario(double salario) {
        if (salario <= 600) return 3.0;
        else if (salario <= 900) return 4.0;
        else return 5.0;
    }

    // MÉTODOS QUE SE USARÁN EN EL CONTROLLER
    public List<Prestamo> obtenerPrestamosPorCliente(Integer idCliente) {
        return prestamoRepo.findByCliente_IdUsuario(idCliente);
    }

    public List<Prestamo> obtenerPrestamosEnEspera() {
        return prestamoRepo.findByEstado(EstadoPrestamo.En_espera);
    }

    // MÉTODO NUEVO: Buscar préstamo por ID
    public Prestamo obtenerPrestamoPorId(Integer idPrestamo) {
        return prestamoRepo.findById(idPrestamo)
                .orElseThrow(() -> new NotFoundException("Préstamo no encontrado"));
    }
}