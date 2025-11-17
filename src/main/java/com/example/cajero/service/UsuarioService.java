package com.example.cajero.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cajero.dto.CrearClienteRequest;
import com.example.cajero.dto.CrearDependienteRequest;
import com.example.cajero.entity.Rol;
import com.example.cajero.entity.Usuario;
import com.example.cajero.exception.BadRequestException;
import com.example.cajero.exception.NotFoundException;
import com.example.cajero.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Usuario crearCliente(Usuario u) {
        repo.findByDui(u.getDui()).ifPresent(existing -> {
            throw new BadRequestException("DUI ya registrado");
        });
        repo.findByCorreo(u.getCorreo()).ifPresent(existing -> {
            throw new BadRequestException("Correo ya registrado");
        });
        u.setRol(Rol.Cliente);
        u.setActivo(true);
        return repo.save(u);
    }

    // MÉTODO NUEVO: Crear cliente desde DTO
    @Transactional
    public Usuario crearClienteDesdeRequest(CrearClienteRequest request) {
        // Validar que el DUI no exista
        if (repo.findByDui(request.getDui()).isPresent()) {
            throw new BadRequestException("El DUI ya está registrado");
        }

        Usuario cliente = new Usuario();
        cliente.setNombre(request.getNombre());
        cliente.setDui(request.getDui());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setCorreo(request.getCorreo());
        cliente.setContrasena(request.getContrasena());
        cliente.setSalario(request.getSalario());
        cliente.setRol(Rol.Cliente);
        cliente.setActivo(true);

        return repo.save(cliente);
    }

    // MÉTODO NUEVO: Crear dependiente
    @Transactional
    public Usuario crearDependiente(CrearDependienteRequest request) {
        // Validar que el DUI no exista
        if (repo.findByDui(request.getDui()).isPresent()) {
            throw new BadRequestException("El DUI ya está registrado");
        }

        Usuario dependiente = new Usuario();
        dependiente.setNombre(request.getNombre());
        dependiente.setDui(request.getDui());
        dependiente.setTelefono(request.getTelefono());
        dependiente.setCorreo(request.getCorreo());
        dependiente.setContrasena(request.getContrasena());
        dependiente.setRol(Rol.Dependiente);
        dependiente.setActivo(true);

        return repo.save(dependiente);
    }

    // MÉTODO NUEVO: Validar máximo de cuentas
    public boolean validarMaximoCuentas(Integer usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getCuentas().size() >= 3;
    }

    // MÉTODO NUEVO: Buscar por DUI con validación
    public Usuario buscarClientePorDui(String dui) {
        Usuario usuario = buscarPorDui(dui);
        if (usuario.getRol() != Rol.Cliente) {
            throw new BadRequestException("El DUI no pertenece a un cliente");
        }
        return usuario;
    }

    // MÉTODO NUEVO: Obtener todos los clientes activos
    public List<Usuario> obtenerClientesActivos() {
        return repo.findByRolAndActivoTrue(Rol.Cliente);
    }

    public Usuario buscarPorDui(String dui) {
        return repo.findByDui(dui)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con DUI: " + dui));
    }

    public Usuario buscarPorId(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado id: " + id));
    }
}