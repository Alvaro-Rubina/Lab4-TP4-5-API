package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoResponseDTO;
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

    @Autowired
    private InstrumentoService instrumentoService;

    public void savePedido(PedidoDTO pedidoDTO) {
        Pedido pedido = toEntity(pedidoDTO);
        Pedido pedidoGuardado = pedidoRepo.save(pedido);

        detallePedidoService.saveMultipleDetallePedidos(pedidoDTO.getDetallePedidos(), pedidoGuardado);

        // TODO: Esto podria cambiarlo para que se calcule en base a la entidad en lugar del dto
        Double total = calcularTotalPedido(pedidoDTO.getDetallePedidos());
        pedidoGuardado.setTotalPedido(total);

        pedidoRepo.save(pedidoGuardado);
    }

    public PedidoResponseDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido con el id " + id + " no encontrado"));
        return toDTO(pedido);
    }

    public List<PedidoResponseDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepo.findAll();
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // MAPPERS
    private Pedido toEntity(PedidoDTO pedidoDTO) {
        return Pedido.builder()
                .fechaPedido(LocalDate.now())
                .build();
    }

    public PedidoResponseDTO toDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .fechaPedido(pedido.getFechaPedido())
                .totalPedido(pedido.getTotalPedido())
                .detallePedidos(pedido.getDetallePedidos().stream()
                        .map(detallePedidoService::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    // Metodos adicionales
    public Double calcularTotalPedido(List<DetallePedidoDTO> detallePedidos) {
        Double totalPedido = 0.0;
        for (DetallePedidoDTO detallePedido : detallePedidos) {
            InstrumentoDTO instrumentoDTO = instrumentoService.getInstrumentoById(detallePedido.getInstrumentoId());
            totalPedido += instrumentoDTO.getPrecio() * detallePedido.getCantidad();
        }
        return totalPedido;
    }
}
