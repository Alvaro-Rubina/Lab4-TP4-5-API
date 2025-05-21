package org.spdgrupo.lab4tp45api.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.spdgrupo.lab4tp45api.model.PreferenceMP;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoResponseDTO;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.spdgrupo.lab4tp45api.service.ExcelService;
import org.spdgrupo.lab4tp45api.service.MercadoPagoService;
import org.spdgrupo.lab4tp45api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @Autowired
    private ExcelService excelService;

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

    @GetMapping("/reportes")
    @ResponseBody
    public ResponseEntity<byte[]> generarReporteExcel(@RequestParam LocalDate fechaInicio,
                                                 @RequestParam LocalDate fechaFin) throws IOException
    {
        SXSSFWorkbook libroExcel = excelService.generarReporteExcel(fechaInicio, fechaFin);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        libroExcel.write(stream);
        libroExcel.dispose();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reporte_pedidos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(stream.toByteArray());

    }

    @GetMapping("/bar-chart")
    public ResponseEntity<List<List<Object>>> getBarChartData() {
        return ResponseEntity.ok(pedidoService.getPedidosGroupedByMesAnio());
    }

    @GetMapping("/pie-chart")
    public ResponseEntity<List<List<Object>>> getPieChartData() {
        return ResponseEntity.ok(pedidoService.getPedidosGroupedByInstrumento());
    }

}

