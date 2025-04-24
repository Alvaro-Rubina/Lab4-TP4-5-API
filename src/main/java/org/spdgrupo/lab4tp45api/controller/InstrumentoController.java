package org.spdgrupo.lab4tp45api.controller;

import jakarta.validation.Valid;
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;
import org.spdgrupo.lab4tp45api.service.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instrumentos")
@CrossOrigin(origins = "http://localhost:5173")
class InstrumentoController {

    @Autowired
    private InstrumentoService instrumentoService;

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
