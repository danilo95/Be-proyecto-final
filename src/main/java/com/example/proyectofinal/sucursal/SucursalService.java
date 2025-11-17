package com.example.proyectofinal.sucursal;

import com.example.proyectofinal.sucursal.dto.SucursalDto;
import com.example.proyectofinal.usuario.empleado.Sucursal;
import com.example.proyectofinal.usuario.empleado.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    @Transactional
    public SucursalDto crear(CrearSucursalRequest req) {
        Sucursal s = new Sucursal();
        s.setNombre(req.getNombre());
        s.setDireccion(req.getDireccion());
        s.setIdGerenteSucursal(req.getIdGerenteSucursal());
        Sucursal saved = sucursalRepository.save(s);
        return toDto(saved);
    }

    public SucursalDto obtenerPorId(Integer id) {
        Optional<Sucursal> opt = sucursalRepository.findById(id);
        return opt.map(this::toDto).orElse(null);
    }

    public List<SucursalDto> listarTodas() {
        return sucursalRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public SucursalDto actualizar(Integer id, CrearSucursalRequest req) {
        return sucursalRepository.findById(id).map(s -> {
            s.setNombre(req.getNombre());
            s.setDireccion(req.getDireccion());
            s.setIdGerenteSucursal(req.getIdGerenteSucursal());
            return toDto(sucursalRepository.save(s));
        }).orElse(null);
    }

    @Transactional
    public boolean eliminar(Integer id) {
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SucursalDto toDto(Sucursal s) {
        SucursalDto d = new SucursalDto();
        d.setIdSucursal(s.getIdSucursal());
        d.setNombre(s.getNombre());
        d.setDireccion(s.getDireccion());
        d.setIdGerenteSucursal(s.getIdGerenteSucursal());
        return d;
    }
}
