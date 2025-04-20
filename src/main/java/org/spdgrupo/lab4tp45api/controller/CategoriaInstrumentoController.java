package org.spdgrupo.lab4tp45api.controller;

import org.spdgrupo.lab4tp45api.model.dto.CategoriaInstrumentoDTO;
import org.spdgrupo.lab4tp45api.service.CategoriaInstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
class CategoriaInstrumentoController {

    @Autowired
    private CategoriaInstrumentoService categoriaInstrumentoService;

    @PostMapping
    public ResponseEntity<String> saveCategoria(@RequestBody CategoriaInstrumentoDTO categoriaDTO) {
        categoriaInstrumentoService.saveCategoriaInstrumento(categoriaDTO);
        return ResponseEntity.ok("Categoría guardada correctamente");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CategoriaInstrumentoDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaInstrumentoDTO categoriaDTO = categoriaInstrumentoService.getCategoriaInstrumentoById(id);
        return ResponseEntity.ok(categoriaDTO);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CategoriaInstrumentoDTO>> getAllCategorias() {
        List<CategoriaInstrumentoDTO> categorias = categoriaInstrumentoService.getAllCategoriaInstrumentos();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategoria(@PathVariable Long id,
                                                  @RequestBody CategoriaInstrumentoDTO categoriaDTO) {
        categoriaInstrumentoService.updateCategoriaInstrumento(id, categoriaDTO);
        return ResponseEntity.ok("Categoría actualizada correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteCategoriaById(@PathVariable Long id) {
        categoriaInstrumentoService.deleteCategoriaInstrumento(id);
        return ResponseEntity.ok("Categoría eliminada correctamente");
    }
}