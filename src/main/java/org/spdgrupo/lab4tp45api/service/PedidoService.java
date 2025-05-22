package org.spdgrupo.lab4tp45api.service;

import jakarta.transaction.Transactional;
import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoResponseDTO;
import org.spdgrupo.lab4tp45api.model.entity.DetallePedido;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.spdgrupo.lab4tp45api.repository.DetallePedidoRepository;
import org.spdgrupo.lab4tp45api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private DetallePedidoRepository detallePedidoRepo;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private InstrumentoService instrumentoService;

    @Transactional
    public Pedido savePedido(PedidoDTO pedidoDTO) {
        Pedido pedido = toEntity(pedidoDTO);

        // Aumento la cantidad de vendidos de instrumento
        for (DetallePedido detallePedido : pedido.getDetallePedidos()) {
            Long idInstrumento = detallePedido.getInstrumento().getId();
            int cantidadVendida = detallePedido.getCantidad();
            instrumentoService.aumentarCantidadVendida(idInstrumento, cantidadVendida);
        }
        return pedidoRepo.save(pedido);
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

    public List<PedidoResponseDTO> getPedidosByFechaRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        List<Pedido> pedidos = pedidoRepo.findByFechaPedidoBetween(startDate, endDate);
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<List<Object>> getPedidosGroupedByMesAnio() {
        List<Object[]> resultados = pedidoRepo.countPedidosGroupedByMesAnio();

        List<List<Object>> data = new ArrayList<>();
        data.add(List.of("Año - Mes", "Cantidad de Pedidos"));

        for (Object[] row : resultados) {
            String mesAnio = (String) row[0]; // Ej: "2024-05"
            Long cantidad = (Long) row[1];
            data.add(List.of(mesAnio, cantidad));
        }

        return data;
    }

    public List<List<Object>> getPedidosGroupedByInstrumento() {
        List<Object[]> resultados = detallePedidoRepo.countPedidosByInstrumento();

        List<List<Object>> data = new ArrayList<>();
        data.add(List.of("Instrumento", "Cantidad de Pedidos"));

        for (Object[] row : resultados) {
            String nombreInstrumento = (String) row[0];
            Long cantidad = (Long) row[1];
            data.add(List.of(nombreInstrumento, cantidad));
        }

        return data;
    }



    // MAPPERS
    private Pedido toEntity(PedidoDTO pedidoDTO) {
        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDate.now())
                .titulo("Pedido Musica Hendrix")
                .totalPedido(0.0)
                .detallePedidos(new ArrayList<>())
                .build();

        // DetallePedidos
        for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetallePedidos()) {
            DetallePedido detalle = detallePedidoService.toEntity(detalleDTO);
            detalle.setPedido(pedido);
            pedido.getDetallePedidos().add(detalle);
        }
        pedido.setTotalPedido(calcularTotalPedido(pedido.getDetallePedidos()));

        return pedido;
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
    private Double calcularTotalPedido(List<DetallePedido> detallePedidos) {
        Double totalVenta = 0.0;

        for (DetallePedido detallePedido : detallePedidos) {
            totalVenta += detallePedido.getSubTotal();
        }
        return totalVenta;
    }
}
