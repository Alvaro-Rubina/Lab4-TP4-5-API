package org.spdgrupo.lab4tp45api.controller;

import com.itextpdf.text.DocumentException;
import jakarta.validation.Valid;
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;
import org.spdgrupo.lab4tp45api.service.PdfService;
import org.spdgrupo.lab4tp45api.service.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/instrumentos")
@CrossOrigin(origins = "http://localhost:5173")
class InstrumentoController {

    @Autowired
    private InstrumentoService instrumentoService;

    @Autowired
    private PdfService pdfService;

    @PostMapping
    public ResponseEntity<String> saveInstrumento(@Valid @RequestBody InstrumentoDTO instrumentoDTO) {
        instrumentoService.saveInstrumento(instrumentoDTO);
        return ResponseEntity.ok("Instrumento guardado correctamente");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<InstrumentoDTO> getInstrumentoById(@PathVariable Long id) {
        InstrumentoDTO instrumentoDTO = instrumentoService.getInstrumentoById(id);
        return ResponseEntity.ok(instrumentoDTO);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<InstrumentoDTO>> getAllInstrumentos() {
        List<InstrumentoDTO> instrumentos = instrumentoService.getAllInstrumentos();
        return ResponseEntity.ok(instrumentos);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportInstrumentoPdf(@RequestParam Long idInstrumento) throws IOException, DocumentException {
        byte[] pdfBytes = pdfService.exportInstrumentoPdf(idInstrumento);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=instrumento_" + idInstrumento + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInstrumento(@PathVariable Long id,
                                                    @Valid @RequestBody InstrumentoDTO instrumentoDTO) {
        instrumentoService.updateInstrumento(id, instrumentoDTO);
        return ResponseEntity.ok("Instrumento actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstrumentoById(@PathVariable Long id) {
        instrumentoService.deleteInstrumento(id);
        return ResponseEntity.ok("Instrumento eliminado correctamente");
    }
}
