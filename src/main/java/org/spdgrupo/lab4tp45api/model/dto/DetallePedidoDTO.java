package org.spdgrupo.lab4tp45api.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoDTO {

    private Long id;
    private int cantidad;
    private Double subTotal;
    private PedidoDTO pedido;
    private InstrumentoDTO instrumento;
}
