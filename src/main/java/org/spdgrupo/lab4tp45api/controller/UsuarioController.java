package org.spdgrupo.lab4tp45api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spdgrupo.lab4tp45api.model.dto.usuario.UsuarioDTO;
import org.spdgrupo.lab4tp45api.model.dto.usuario.UsuarioResponseDTO;
import org.spdgrupo.lab4tp45api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.register(usuarioDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.login(usuarioDTO);
            return ResponseEntity.ok(usuarioResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
