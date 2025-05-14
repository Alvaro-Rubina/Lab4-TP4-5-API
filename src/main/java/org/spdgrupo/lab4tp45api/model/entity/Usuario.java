package org.spdgrupo.lab4tp45api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spdgrupo.lab4tp45api.model.enums.Rol;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String clave;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
