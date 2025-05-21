package org.spdgrupo.lab4tp45api.service;

import com.lowagie.text.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoResponseDTO;
import org.spdgrupo.lab4tp45api.model.dto.pedido.PedidoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private PedidoService pedidoService;

    // Excel
    protected static Font titulo = new Font(Font.COURIER, 14, Font.BOLD);
    protected static Font redFont = new Font(Font.COURIER, 12, Font.NORMAL, Color.RED);
    protected static Font textoHeader = new Font(Font.COURIER, 17, Font.BOLD);
    protected static Font texto = new Font(Font.COURIER, 11, Font.NORMAL);
    protected static Font textoBold = new Font(Font.COURIER, 11, Font.BOLD);
    protected static Font textoMini = new Font(Font.COURIER, 8, Font.NORMAL);
    protected static Font textoMiniBold = new Font(Font.COURIER, 8, Font.BOLD);
    protected static Font textoBig = new Font(Font.COURIER, 50, Font.BOLD);



    public SXSSFWorkbook generarReporteExcel(LocalDate fechaInicio, LocalDate fechaFin) {
        // Se crea el libro con tamaño de paginación de 50
        SXSSFWorkbook libro = new SXSSFWorkbook(50);

        // Se crea una hoja dentro del libro
        SXSSFSheet hoja = libro.createSheet("Reporte de Pedidos");

        // Crear estilo para el encabezado
        XSSFFont font = (XSSFFont) libro.createFont();
        font.setBold(true);
        XSSFCellStyle style = (XSSFCellStyle) libro.createCellStyle();
        style.setFont(font);

        // Crear encabezados
        SXSSFRow headerRow = hoja.createRow(0);
        String[] headers = {"Fecha", "Instrumento", "Marca", "Modelo", "Cantidad", "Precio Unitario", "Subtotal"};
        for (int i = 0; i < headers.length; i++) {
            SXSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }

        // Obtener datos
        List<PedidoResponseDTO> pedidos = pedidoService.getPedidosByFechaRange(fechaInicio, fechaFin);

        int rowNum = 1;
        // Llenar datos
        for (PedidoResponseDTO pedido : pedidos) {
            for (DetallePedidoResponseDTO detalle : pedido.getDetallePedidos()) {
                SXSSFRow row = hoja.createRow(rowNum++);

                // Fecha
                row.createCell(0).setCellValue(pedido.getFechaPedido().toString());

                // Instrumento
                row.createCell(1).setCellValue(detalle.getInstrumento().getInstrumento());

                // Marca
                row.createCell(2).setCellValue(detalle.getInstrumento().getMarca());

                // Modelo
                row.createCell(3).setCellValue(detalle.getInstrumento().getModelo());

                // Cantidad
                row.createCell(4).setCellValue(detalle.getCantidad());

                // Precio Unitario
                row.createCell(5).setCellValue(detalle.getInstrumento().getPrecio());

                // Subtotal
                row.createCell(6).setCellValue(detalle.getSubTotal());
            }
        }

        // Autoajustar el ancho de las columnas
        for (int i = 0; i < headers.length; i++) {
            hoja.trackAllColumnsForAutoSizing();
            hoja.autoSizeColumn(i);
        }


        return libro;
    }
}
