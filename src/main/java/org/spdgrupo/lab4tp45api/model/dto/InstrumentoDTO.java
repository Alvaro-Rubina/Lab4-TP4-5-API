package org.spdgrupo.lab4tp45api.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InstrumentoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String instrumento;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "La imagen es obligatoria")
    private String imagen;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio no puede ser negativo")
    private Double precio;

    @NotBlank(message = "El costo de envio es obligatorio")
    private String costoEnvio;

    @NotNull(message = "La cantidad vendida es obligatoria")
    @PositiveOrZero(message = "La cantidad vendida debe ser positiva o cero")
    private int cantidadVendida;
    private boolean activo;
    private CategoriaInstrumentoDTO categoria;
}
