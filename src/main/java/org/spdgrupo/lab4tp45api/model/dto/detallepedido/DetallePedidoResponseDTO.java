package org.spdgrupo.lab4tp45api.model.dto.detallepedido;

import lombok.*;
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoResponseDTO {

    private Long id;
    private int cantidad;
    private Double subTotal;
    private InstrumentoDTO instrumento;
}
