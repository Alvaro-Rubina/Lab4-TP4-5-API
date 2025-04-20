package org.spdgrupo.lab4tp45api.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InstrumentoDTO {

    private Long id;
    private String nombre;
    private String marca;
    private String modelo;
    private String descripcion;
    private String imagen;
    private Double precio;
    private String costoEnvio;
    private int cantidadVendida;
    private Boolean activo;
    private CategoriaInstrumentoDTO categoria;
}
