package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.DetallePedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.PedidoDTO;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.spdgrupo.lab4tp45api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;
    @Autowired
    private DetallePedidoService detallePedidoService;

    public void savePedido(PedidoDTO pedidoDTO) {
        Pedido pedido = toEntity(pedidoDTO);
        Pedido pedidoGuardado = pedidoRepo.save(pedido);

        // Una vez guardado el producto, se guardan los detalleProductos (necesitan el id del pedido)
        detallePedidoService.saveMultipleDetallePedidos(pedidoDTO.getDetallePedidos(), pedidoGuardado.getId());
    }

    public PedidoDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido con el id " + id + " no encontrado"));
        return toDTO(pedido);
    }

    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepo.findAll();
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // MAPPERS
    private Pedido toEntity(PedidoDTO pedidoDTO) {
        return Pedido.builder()
                .fechaPedido(LocalDate.now())
                .totalPedido(calcularTotalPedido(pedidoDTO.getDetallePedidos()))
                .build();
    }

    public PedidoDTO toDTO(Pedido pedido) {
        return PedidoDTO.builder()
                .id(pedido.getId())
                .fechaPedido(pedido.getFechaPedido())
                .totalPedido(pedido.getTotalPedido())
                .detallePedidos(pedido.getDetallePedidos().stream()
                        .map(detallePedidoService::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    // Metodos adicionales
    private Double calcularTotalPedido(List<DetallePedidoDTO> detallePedidosDTO) {
        Double totalPedido = 0.0;

        for (DetallePedidoDTO detallePedidoDTO : detallePedidosDTO) {
            totalPedido += detallePedidoDTO.getSubTotal();
        }
        return totalPedido;
    }
}
