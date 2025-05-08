package org.spdgrupo.lab4tp45api.model.dto.pedido;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoDTO;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoDTO {

    @Size(min = 1, message = "El campo detallePedidos debe contener por lo menos 1 elemento")
    private List<DetallePedidoDTO> detallePedidos;

}
