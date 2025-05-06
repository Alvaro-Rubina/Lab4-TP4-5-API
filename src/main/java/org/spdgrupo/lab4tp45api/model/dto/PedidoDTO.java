package org.spdgrupo.lab4tp45api.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoDTO {

    private Long id;
    private LocalDate fechaPedido;
    private Double totalPedido;
    private List<DetallePedidoDTO> detallePedidos;

}
