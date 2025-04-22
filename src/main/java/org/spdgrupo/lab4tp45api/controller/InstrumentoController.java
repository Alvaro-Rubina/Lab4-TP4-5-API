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
}
