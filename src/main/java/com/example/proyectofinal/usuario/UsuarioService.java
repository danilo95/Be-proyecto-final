package com.example.proyectofinal.usuario;

import com.example.proyectofinal.usuario.dto.UsuarioListaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
}
