package org.spdgrupo.lab4tp45api.model.dto.usuario;

import lombok.*;
import org.spdgrupo.lab4tp45api.model.enums.Rol;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombreUsuario;
    private String clave;
    private Rol rol;
}
