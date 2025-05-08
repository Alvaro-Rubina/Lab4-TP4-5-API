package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoResponseDTO;
import org.spdgrupo.lab4tp45api.model.entity.DetallePedido;
import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.spdgrupo.lab4tp45api.repository.DetallePedidoRepository;
import org.spdgrupo.lab4tp45api.repository.InstrumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepo;

    @Autowired
    private InstrumentoRepository instrumentoRepo;

    @Autowired
    private InstrumentoService instrumentoService;

    public void saveMultipleDetallePedidos(List<DetallePedidoDTO> dtoList, Pedido pedido) {
        for (DetallePedidoDTO dto : dtoList) {
            DetallePedido detalle = toEntity(dto, pedido);
            detallePedidoRepo.save(detalle);
        }
    }


    // MAPPERS
    public DetallePedido toEntity(DetallePedidoDTO dto, Pedido pedido) {
        Instrumento instrumento = instrumentoRepo.findById(dto.getInstrumentoId())
                .orElseThrow(() -> new NotFoundException("Instrumento con el id " + dto.getInstrumentoId() + " no encontrado"));

        Double subTotal = instrumento.getPrecio() * dto.getCantidad();

        return DetallePedido.builder()
                .cantidad(dto.getCantidad())
                .subTotal(subTotal)
                .instrumento(instrumento)
                .pedido(pedido)
                .build();
    }

    public DetallePedidoResponseDTO toDTO(DetallePedido detallePedido) {
        return DetallePedidoResponseDTO.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .subTotal(detallePedido.getSubTotal())
                .instrumento(instrumentoService.toDTO(detallePedido.getInstrumento()))
                .build();
    }
}
