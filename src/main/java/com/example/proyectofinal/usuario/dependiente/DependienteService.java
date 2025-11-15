package com.example.proyectofinal.usuario.dependiente;

import com.example.proyectofinal.usuario.Usuario;
import com.example.proyectofinal.usuario.UsuarioRepository;
import com.example.proyectofinal.usuario.dependiente.dto.CrearDependienteRequest;
import com.example.proyectofinal.usuario.dependiente.dto.DependienteDto;
import org.springframework.stereotype.Service;

@Service
public class DependienteService {

    private final DependienteRepository dependienteRepository;
    private final UsuarioRepository usuarioRepository;

    public DependienteService(DependienteRepository dependienteRepository,
                              UsuarioRepository usuarioRepository) {
        this.dependienteRepository = dependienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public DependienteDto crearDependiente(CrearDependienteRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dependienteRepository.existsById(usuario.getIdUsuario())) {
            throw new RuntimeException("Este usuario ya es dependiente");
        }

        Dependiente dep = new Dependiente();
        dep.setUsuario(usuario);
        dep.setNombreComercio(request.getNombreComercio());
        dep.setPorcentajeComision(request.getPorcentajeComision());

        dep = dependienteRepository.save(dep);

        DependienteDto dto = new DependienteDto();
        dto.setIdDependiente(dep.getIdDependiente());
        dto.setNombre(usuario.getNombre());
        dto.setNombreComercio(dep.getNombreComercio());
        dto.setPorcentajeComision(dep.getPorcentajeComision());
        return dto;
    }
}
