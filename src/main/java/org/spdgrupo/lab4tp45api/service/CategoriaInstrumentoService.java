package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.model.dto.CategoriaInstrumentoDTO;
import org.spdgrupo.lab4tp45api.model.entity.CategoriaInstrumento;
import org.spdgrupo.lab4tp45api.repository.CategoriaInstrumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaInstrumentoService {

    @Autowired
    private CategoriaInstrumentoRepository categoriaInstrumentoRepo;

    // MAPPERS
    private CategoriaInstrumento toEntity(CategoriaInstrumentoDTO categoriaInstrumentoDTO) {
        return CategoriaInstrumento.builder()
                .nombre(categoriaInstrumentoDTO.getNombre())
                .build();
    }

    public CategoriaInstrumentoDTO toDTO(CategoriaInstrumento categoriaInstrumento) {
        return CategoriaInstrumentoDTO.builder()
                .id(categoriaInstrumento.getId())
                .nombre(categoriaInstrumento.getNombre())
                .build();
    }
}
