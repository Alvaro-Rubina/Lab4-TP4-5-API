package org.spdgrupo.lab4tp45api.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.spdgrupo.lab4tp45api.repository.InstrumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Optional;

@Service
public class ExportPdfService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    public byte[] exportInstrumentoPdf(Long idInstrumento) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Optional<Instrumento> optInstrumento = instrumentoRepository.findById(idInstrumento);
            if (!optInstrumento.isPresent()) return null;

            Instrumento instrumento = optInstrumento.get();

            Document document = new Document(PageSize.A4, 30, 30, 30, 30);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Título: nombre del instrumento
            Font titulo = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.BLUE);
            Paragraph header = new Paragraph(instrumento.getInstrumento(), titulo);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            document.add(new Paragraph(" ")); // Espacio

            // Imagen centrada
            if (instrumento.getImagen() != null && !instrumento.getImagen().isEmpty()) {
                try {
                    Image img = Image.getInstance(new URL(instrumento.getImagen()));
                    img.scaleToFit(250, 250);
                    img.setAlignment(Element.ALIGN_CENTER);
                    document.add(img);
                    document.add(new Paragraph(" "));
                } catch (Exception e) {
                    // Si la imagen falla, solo ignora y sigue
                }
            }

            // Precio grande y centrado usando tabla de una celda
            Font precioFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            PdfPTable precioTable = new PdfPTable(1);
            precioTable.setWidthPercentage(80);
            precioTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell precioCell = new PdfPCell(new Phrase("Precio: $" + instrumento.getPrecio(), precioFont));
            precioCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            precioCell.setBorder(Rectangle.NO_BORDER);
            precioTable.addCell(precioCell);
            document.add(precioTable);

            document.add(new Paragraph(" ")); // Espacio

            // Tabla de atributos (2 columnas)
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Fila 1: Marca y Modelo
            table.addCell(createLeftCell("Marca: " + instrumento.getMarca(), valueFont));
            table.addCell(createLeftCell("Modelo: " + instrumento.getModelo(), valueFont));

            // Fila 2: Costo Envío y Cantidad Vendida
            table.addCell(createLeftCell("Costo Envío: " + instrumento.getCostoEnvio(), valueFont));
            table.addCell(createLeftCell("Cantidad Vendida: " + instrumento.getCantidadVendida(), valueFont));

            // Fila 3: Categoría y Activo
            String categoria = instrumento.getCategoria() != null ? instrumento.getCategoria().getNombre() : "Sin categoría";
            table.addCell(createLeftCell("Categoría: " + categoria, valueFont));
            table.addCell(createLeftCell("Activo: " + (instrumento.isActivo() ? "Sí" : "No"), valueFont));

            // Fila 4: Descripción (solo columna izquierda)
            PdfPCell descCell = new PdfPCell(new Phrase("Descripción: " + instrumento.getDescripcion(), valueFont));
            descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            descCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(descCell);

            // Celda vacía a la derecha
            PdfPCell emptyCell = new PdfPCell(new Phrase(""));
            emptyCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(emptyCell);

            document.add(table);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PdfPCell createLeftCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}