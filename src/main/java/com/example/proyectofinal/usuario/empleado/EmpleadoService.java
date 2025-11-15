package com.example.proyectofinal.usuario.empleado;

import com.example.proyectofinal.usuario.Usuario;
import com.example.proyectofinal.usuario.UsuarioRepository;
import com.example.proyectofinal.usuario.empleado.dto.CrearEmpleadoRequest;
import com.example.proyectofinal.usuario.empleado.dto.EmpleadoDto;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SucursalRepository sucursalRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           UsuarioRepository usuarioRepository,
                           SucursalRepository sucursalRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.sucursalRepository = sucursalRepository;
    }

    public EmpleadoDto crearEmpleado(CrearEmpleadoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (empleadoRepository.existsById(usuario.getIdUsuario())) {
            throw new RuntimeException("Este usuario ya es empleado");
        }

        Sucursal sucursal = sucursalRepository.findById(request.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        Empleado emp = new Empleado();
        emp.setUsuario(usuario);
        emp.setCargo(request.getCargo());
        emp.setEstado(request.getEstado());
        emp.setSucursal(sucursal);

        emp = empleadoRepository.save(emp);

        EmpleadoDto dto = new EmpleadoDto();
        dto.setIdEmpleado(emp.getIdEmpleado());
        dto.setNombre(usuario.getNombre());
        dto.setCargo(emp.getCargo());
        dto.setEstado(emp.getEstado());
        dto.setIdSucursal(sucursal.getIdSucursal());
        dto.setNombreSucursal(sucursal.getNombre());
        return dto;
    }
}
