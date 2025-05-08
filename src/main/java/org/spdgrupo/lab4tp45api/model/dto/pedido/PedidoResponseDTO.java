package org.spdgrupo.lab4tp45api.model.dto.pedido;

import lombok.*;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoResponseDTO;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoResponseDTO {

    private Long id;
    private LocalDate fechaPedido;
    private Double totalPedido;
    private List<DetallePedidoResponseDTO> detallePedidos;
}
