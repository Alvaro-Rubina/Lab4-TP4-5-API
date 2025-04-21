package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.model.dto.CategoriaInstrumentoDTO;
import org.spdgrupo.lab4tp45api.model.entity.CategoriaInstrumento;
import org.spdgrupo.lab4tp45api.repository.CategoriaInstrumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaInstrumentoService {

    @Autowired
    private CategoriaInstrumentoRepository categoriaInstrumentoRepo;

    public void saveCategoriaInstrumento(CategoriaInstrumentoDTO categoriaInstrumentoDTO) {
        CategoriaInstrumento categoriaInstrumento = toEntity(categoriaInstrumentoDTO);
        categoriaInstrumentoRepo.save(categoriaInstrumento);
    }

    public CategoriaInstrumentoDTO getCategoriaInstrumentoById(Long id) {
        CategoriaInstrumento categoriaInstrumento = categoriaInstrumentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaInstrumento con el id " + id + " no encontrado"));
        return toDTO(categoriaInstrumento);
    }

    public List<CategoriaInstrumentoDTO> getAllCategoriaInstrumentos() {
        List<CategoriaInstrumento> categoriaInstrumentos = categoriaInstrumentoRepo.findAll();
        List<CategoriaInstrumentoDTO> categoriaInstrumentoDTOs = new ArrayList<>();

        for (CategoriaInstrumento categoriaInstrumento : categoriaInstrumentos) {
            categoriaInstrumentoDTOs.add(toDTO(categoriaInstrumento));
        }
        return categoriaInstrumentoDTOs;
    }

    public void updateCategoriaInstrumento(Long id, CategoriaInstrumentoDTO categoriaInstrumentoDTO) {
        CategoriaInstrumento categoriaInstrumento = categoriaInstrumentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaInstrumento con el id " + id + " no encontrado"));

        if (!categoriaInstrumento.getNombre().equals(categoriaInstrumentoDTO.getNombre())) {
            categoriaInstrumento.setNombre(categoriaInstrumentoDTO.getNombre());
        }

        if (categoriaInstrumento.isActivo() != categoriaInstrumentoDTO.isActivo()) {
            categoriaInstrumento.setActivo(categoriaInstrumentoDTO.isActivo());
        }

        categoriaInstrumentoRepo.save(categoriaInstrumento);
    }

    public void deleteCategoriaInstrumento(Long id) {
        CategoriaInstrumento categoriaInstrumento = categoriaInstrumentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaInstrumento con el id " + id + " no encontrado"));
        categoriaInstrumento.setActivo(false);
        categoriaInstrumentoRepo.save(categoriaInstrumento);
    }

    // MAPPERS
    private CategoriaInstrumento toEntity(CategoriaInstrumentoDTO categoriaInstrumentoDTO) {
        return CategoriaInstrumento.builder()
                .nombre(categoriaInstrumentoDTO.getNombre())
                .activo(categoriaInstrumentoDTO.isActivo())
                .build();
    }

    public CategoriaInstrumentoDTO toDTO(CategoriaInstrumento categoriaInstrumento) {
        return CategoriaInstrumentoDTO.builder()
                .id(categoriaInstrumento.getId())
                .nombre(categoriaInstrumento.getNombre())
                .activo(categoriaInstrumento.isActivo())
                .build();
    }
}
