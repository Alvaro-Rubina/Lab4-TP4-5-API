package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.DetallePedidoDTO;
import org.spdgrupo.lab4tp45api.model.entity.DetallePedido;
import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.spdgrupo.lab4tp45api.repository.DetallePedidoRepository;
import org.spdgrupo.lab4tp45api.repository.InstrumentoRepository;
import org.spdgrupo.lab4tp45api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private InstrumentoRepository instrumentoRepo;

    @Autowired
    @Lazy
    private PedidoService pedidoService;

    @Autowired
    private InstrumentoService instrumentoService;

    public void saveDetallePedido(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = toEntity(detallePedidoDTO);
        detallePedidoRepo.save(detallePedido);
    }

    public void saveMultipleDetallePedidos(List<DetallePedidoDTO> detallePedidoDTOList, Long pedidoId) {
        detallePedidoDTOList.forEach(dto -> {dto.setId(pedidoId);});

        List<DetallePedido> detalles = detallePedidoDTOList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        detallePedidoRepo.saveAll(detalles);

        detalles.forEach(detalle -> {
            Pedido pedido = pedidoRepo.findById(pedidoId)
                    .orElseThrow(() -> new NotFoundException("Pedido con el id " + pedidoId + " no encontrado"));
            detalle.setPedido(pedido);
        });
        detallePedidoRepo.saveAll(detalles);
    }

    // MAPPERS
    private DetallePedido toEntity(DetallePedidoDTO detallePedidoDTO) {

        Instrumento instrumento = instrumentoRepo.findById(detallePedidoDTO.getInstrumento().getId())
                .orElseThrow(() -> new NotFoundException("Instrumento con el id " + detallePedidoDTO.getInstrumento().getId() + " no encontrado"));

        Double subTotal = instrumento.getPrecio() * detallePedidoDTO.getCantidad();

        return DetallePedido.builder()
                .cantidad(detallePedidoDTO.getCantidad())
                .subTotal(subTotal)
                .instrumento(instrumento)
                .build();
    }

    public DetallePedidoDTO toDTO(DetallePedido detallePedido) {
        return DetallePedidoDTO.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .subTotal(detallePedido.getSubTotal())
                .pedido(pedidoService.toDTO(detallePedido.getPedido()))
                .instrumento(instrumentoService.toDTO(detallePedido.getInstrumento()))
                .build();
    }
}
