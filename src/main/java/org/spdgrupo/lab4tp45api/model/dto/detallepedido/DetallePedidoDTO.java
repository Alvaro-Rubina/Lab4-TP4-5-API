package org.spdgrupo.lab4tp45api.model.dto.detallepedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoDTO {

    @NotNull(message = "El campo cantidad no puede ser nulo")
    @Min(value = 1, message = "El campo cantidad no puede ser menor a 1")
    private int cantidad;

    @NotNull(message = "El campo instrumentoId no puede ser nulo")
    @Min(value = 1, message = "El campo instrumentoId no puede ser menor a 1")
    private Long instrumentoId;
}
