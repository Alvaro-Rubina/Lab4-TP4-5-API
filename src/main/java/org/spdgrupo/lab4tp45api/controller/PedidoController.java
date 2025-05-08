package org.spdgrupo.lab4tp45api.controller;

import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoResponseDTO;
import org.spdgrupo.lab4tp45api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<String> savePedido(@RequestBody PedidoDTO pedidoDTO) {
        pedidoService.savePedido(pedidoDTO);
        return ResponseEntity.ok("Pedido guardado correctamente");
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
}
