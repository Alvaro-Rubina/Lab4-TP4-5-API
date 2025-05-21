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

            // Título: nombre del instrumento (negro, normal)
            Font titulo = new Font(Font.FontFamily.HELVETICA, 22, Font.NORMAL, BaseColor.BLACK);
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

            // Precio grande y centrado usando tabla de una celda (negro, normal)
            Font precioFont = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL, BaseColor.BLACK);
            PdfPTable precioTable = new PdfPTable(1);
            precioTable.setWidthPercentage(80);
            precioTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell precioCell = new PdfPCell(new Phrase("Precio: $" + instrumento.getPrecio(), precioFont));
            precioCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            precioCell.setBorder(Rectangle.NO_BORDER);
            precioTable.addCell(precioCell);
            document.add(precioTable);

            document.add(new Paragraph(" ")); // Espacio

            // Tabla de atributos: 2 columnas, 2 filas, sin bordes
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Fila 1: Marca y Modelo
            table.addCell(createCell("Marca: ", instrumento.getMarca(), labelFont, valueFont));
            table.addCell(createCell("Modelo: ", instrumento.getModelo(), labelFont, valueFont));

            // Fila 2: Costo Envío y Descripción
            table.addCell(createCell("Costo Envío: ", instrumento.getCostoEnvio(), labelFont, valueFont));
            table.addCell(createCell("Descripción: ", instrumento.getDescripcion(), labelFont, valueFont));

            document.add(table);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PdfPCell createCell(String label, String value, Font labelFont, Font valueFont) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(label, labelFont));
        phrase.add(new Chunk(value, valueFont));
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(60f);
        cell.setPaddingTop(8f);
        cell.setPaddingBottom(8f);
        return cell;
    }
}