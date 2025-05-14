package org.spdgrupo.lab4tp45api.service;

import lombok.RequiredArgsConstructor;
import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.usuario.UsuarioDTO;
import org.spdgrupo.lab4tp45api.model.dto.usuario.UsuarioResponseDTO;
import org.spdgrupo.lab4tp45api.model.entity.Usuario;
import org.spdgrupo.lab4tp45api.model.enums.Rol;
import org.spdgrupo.lab4tp45api.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // TODO: Acá quizas poner mas logicas o validaciones para registrar
    public UsuarioResponseDTO register(UsuarioDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        usuario = usuarioRepo.save(usuario);
        return toDTO(usuario);
    }

    public UsuarioResponseDTO login(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepo.findByNombreUsuario(usuarioDTO.getNombreUsuario())
                .orElseThrow(() -> new NotFoundException("Nombre de usuario " + usuarioDTO.getNombreUsuario() + " no encontrado"));

        if (!passwordEncoder.matches(usuarioDTO.getClave(), usuario.getClave())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return toDTO(usuario);
    }

    // MAPPERS
    private Usuario toEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nombreUsuario(usuarioDTO.getNombreUsuario())
                .clave(encriptarClave(usuarioDTO.getClave()))
                .rol((usuarioDTO.getRol() != null ? usuarioDTO.getRol() : Rol.CLIENTE))
                .build();
    }

    private UsuarioResponseDTO toDTO (Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .nombreUsuario(usuario.getNombreUsuario())
                /*.clave(usuario.getClave())*/ // NO debería devolver la clave del usuario, aún esté encriptada
                .rol(usuario.getRol())
                .build();
    }

    // Metodos adicionales
    private String encriptarClave(String clave) {
        return passwordEncoder.encode(clave);
    }

}
