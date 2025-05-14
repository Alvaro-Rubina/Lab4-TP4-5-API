package org.spdgrupo.lab4tp45api.model.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.spdgrupo.lab4tp45api.model.enums.Rol;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String clave;

    private Rol rol;
}
