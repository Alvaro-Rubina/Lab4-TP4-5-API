package org.spdgrupo.lab4tp45api.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.spdgrupo.lab4tp45api.model.PreferenceMP;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoResponseDTO;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.spdgrupo.lab4tp45api.service.MercadoPagoService;
import org.spdgrupo.lab4tp45api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping
    public ResponseEntity<String> savePedido(@RequestBody PedidoDTO pedidoDTO) {
        pedidoService.savePedido(pedidoDTO);
        return ResponseEntity.ok("Pedido guardado correctamente");
    }

    // TODO: Habria que aplicar websockets acá
    @PostMapping("/mp")
    public ResponseEntity<PreferenceMP> savePedidoMP(@RequestBody PedidoDTO pedidoDTO) throws MPException, MPApiException {
        Pedido pedido = pedidoService.savePedido(pedidoDTO);
        PreferenceMP preferenceMP = mercadoPagoService.createPreference(pedido);
        return ResponseEntity.ok(preferenceMP);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PedidoResponseDTO> getPedidoById(@PathVariable Long id) {
        PedidoResponseDTO pedidoDTO = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidos() {
        List<PedidoResponseDTO> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/filtrar")
    @ResponseBody
    public ResponseEntity<List<PedidoResponseDTO>> getPedidosByFechaRange (@RequestParam LocalDate fechaInicio,
                                                                           @RequestParam LocalDate fechaFin) {
        List<PedidoResponseDTO> pedidos = pedidoService.getPedidosByFechaRange(fechaInicio, fechaFin);
        return ResponseEntity.ok(pedidos);
    }
}
