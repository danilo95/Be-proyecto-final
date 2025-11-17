package com.example.proyectofinal.usuario.empleado;

import com.example.proyectofinal.usuario.Usuario;
import com.example.proyectofinal.usuario.UsuarioRepository;
import com.example.proyectofinal.usuario.empleado.dto.CrearEmpleadoRequest;
import com.example.proyectofinal.usuario.empleado.dto.EmpleadoDto;
import org.springframework.stereotype.Service;
import com.example.proyectofinal.usuario.empleado.EmpleadoRepository;
import com.example.proyectofinal.usuario.empleado.SucursalRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.proyectofinal.usuario.Rol;

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

    /**
     * Crea un empleado. Se requiere que el creador sea GerenteSucursal de la sucursal indicada.
     * @param request datos para crear empleado (incluye idUsuario ya existente)
     * @param idGerenteId id del usuario que realiza la creación (obtenido del token)
     * @return EmpleadoDto
     */
    public EmpleadoDto crearEmpleado(CrearEmpleadoRequest request, Integer idGerenteId) {
        Usuario gerente = usuarioRepository.findById(idGerenteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gerente no encontrado"));

        if (gerente.getRol() != Rol.GerenteSucursal) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo Gerente de Sucursal puede crear empleados");
        }

        // validar sucursal existe
        var sucursalOpt = sucursalRepository.findById(request.getIdSucursal());
        if (sucursalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada");
        }
        var sucursal = sucursalOpt.get();

        // verificar que el gerente es el gerente asignado a la sucursal
        // usamos el getter de idGerenteSucursal que existe en tu entidad Sucursal
        Integer idGerenteSucursal = sucursal.getIdGerenteSucursal(); // <- usa el getter correcto
        if (idGerenteSucursal == null || !idGerenteSucursal.equals(idGerenteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No eres gerente de esta sucursal");
        }

        // validar usuario a convertir en empleado exista
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario a convertir en empleado no encontrado"));

        // crear Empleado
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

    /**
     * Actualiza el estado del empleado (solo GerenteGeneral puede hacerlo).
     */
    public EmpleadoDto actualizarEstadoEmpleado(Integer idGerenteId, Integer idEmpleado, String nuevoEstado) {
        Usuario gerente = usuarioRepository.findById(idGerenteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gerente no encontrado"));

        if (gerente.getRol() != Rol.GerenteGeneral) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo Gerente General puede actualizar estado de empleado");
        }

        Empleado emp = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));

        emp.setEstado(nuevoEstado);
        emp = empleadoRepository.save(emp);

        EmpleadoDto dto = new EmpleadoDto();
        dto.setIdEmpleado(emp.getIdEmpleado());
        dto.setNombre(emp.getUsuario().getNombre());
        dto.setCargo(emp.getCargo());
        dto.setEstado(emp.getEstado());
        if (emp.getSucursal() != null) {
            dto.setIdSucursal(emp.getSucursal().getIdSucursal());
            dto.setNombreSucursal(emp.getSucursal().getNombre());
        }
        return dto;
    }
}
