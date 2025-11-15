package com.example.proyectofinal.usuario;

import com.example.proyectofinal.usuario.cuenta.CuentaBancariaRepository;
import com.example.proyectofinal.usuario.cuenta.dto.CuentaDto;
import com.example.proyectofinal.usuario.dto.UsuarioDetalleDto;
import com.example.proyectofinal.usuario.dto.UsuarioListaDto;
import com.example.proyectofinal.usuario.prestamo.PrestamoRepository;
import com.example.proyectofinal.usuario.prestamo.dto.PrestamoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CuentaBancariaRepository cuentaRepo;
    private final PrestamoRepository prestamoRepo;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          CuentaBancariaRepository cuentaRepo,
                          PrestamoRepository prestamoRepo) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepo = cuentaRepo;
        this.prestamoRepo = prestamoRepo;
    }


    public List<UsuarioListaDto> obtenerUsuariosVisibles(boolean incluirAdmins) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .filter(u -> incluirAdmins || u.getRol() != Rol.Admin)
                .map(u -> {
                    UsuarioListaDto dto = new UsuarioListaDto();
                    dto.setIdUsuario(u.getIdUsuario());
                    dto.setNombre(u.getNombre());
                    dto.setCorreo(u.getCorreo());
                    dto.setRol(u.getRol());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public UsuarioDetalleDto obtenerUsuarioPorDuiConCuentasYPrestamos(String dui) {
        Usuario usuario = usuarioRepository.findByDui(dui)
                .orElseThrow(() -> new RuntimeException("No existe usuario con ese DUI"));

        List<CuentaDto> cuentas = cuentaRepo.findByCliente_IdUsuario(usuario.getIdUsuario())
                .stream()
                .map(c -> {
                    CuentaDto dto = new CuentaDto();
                    dto.setIdCuenta(c.getIdCuenta());
                    dto.setNumeroCuenta(c.getNumeroCuenta());
                    dto.setSaldo(c.getSaldo());
                    return dto;
                })
                .collect(Collectors.toList());


        List<PrestamoDto> prestamos = prestamoRepo.findByCliente_IdUsuario(usuario.getIdUsuario())
                .stream()
                .map(p -> {
                    PrestamoDto dto = new PrestamoDto();
                    dto.setIdPrestamo(p.getIdPrestamo());
                    dto.setMonto(p.getMonto());
                    dto.setInteres(p.getInteres());
                    dto.setPlazoAnios(p.getPlazoAnios());
                    dto.setCuotaMensual(p.getCuotaMensual());
                    dto.setEstado(p.getEstado());
                    return dto;
                })
                .collect(Collectors.toList());

        UsuarioDetalleDto detalle = new UsuarioDetalleDto();
        detalle.setIdUsuario(usuario.getIdUsuario());
        detalle.setNombre(usuario.getNombre());
        detalle.setDui(usuario.getDui());
        detalle.setCorreo(usuario.getCorreo());
        detalle.setRol(usuario.getRol());
        detalle.setCuentas(cuentas);
        detalle.setPrestamos(prestamos);

        return detalle;
    }
}
