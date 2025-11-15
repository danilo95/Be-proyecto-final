package com.example.proyectofinal.usuario.prestamo;

import com.example.proyectofinal.usuario.Usuario;
import com.example.proyectofinal.usuario.UsuarioRepository;
import com.example.proyectofinal.usuario.prestamo.dto.CrearPrestamoRequest;
import com.example.proyectofinal.usuario.prestamo.dto.PrestamoDto;
import org.springframework.stereotype.Service;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PrestamoDto crearPrestamo(CrearPrestamoRequest request, Integer idCajero) {
        if (request.getMonto() == null || request.getMonto() <= 0) {
            throw new RuntimeException("El monto del préstamo debe ser mayor que cero");
        }

        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Usuario cajero = usuarioRepository.findById(idCajero)
                .orElseThrow(() -> new RuntimeException("Usuario (cajero/gerente/admin) no encontrado"));

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(cliente);
        prestamo.setCajero(cajero);
        prestamo.setMonto(request.getMonto());
        prestamo.setInteres(request.getInteres());
        prestamo.setPlazoAnios(request.getPlazoAnios());
        prestamo.setCuotaMensual(request.getCuotaMensual());
        // aquí respetamos tu tabla: 'En espera'
        prestamo.setEstado("En espera");

        Prestamo guardado = prestamoRepository.save(prestamo);

        PrestamoDto dto = new PrestamoDto();
        dto.setIdPrestamo(guardado.getIdPrestamo());
        dto.setMonto(guardado.getMonto());
        dto.setInteres(guardado.getInteres());
        dto.setPlazoAnios(guardado.getPlazoAnios());
        dto.setCuotaMensual(guardado.getCuotaMensual());
        dto.setEstado(guardado.getEstado());
        dto.setIdCliente(cliente.getIdUsuario());
        dto.setIdCajero(cajero.getIdUsuario());

        return dto;
    }
}
