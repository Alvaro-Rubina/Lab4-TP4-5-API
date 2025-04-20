package org.spdgrupo.lab4tp45api.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoriaInstrumentoDTO {

    private Long id;
    private String nombre;
    private Boolean activo;
}
