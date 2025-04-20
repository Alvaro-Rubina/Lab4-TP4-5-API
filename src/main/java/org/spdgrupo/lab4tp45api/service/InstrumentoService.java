package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;
import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.spdgrupo.lab4tp45api.repository.CategoriaInstrumentoRepository;
import org.spdgrupo.lab4tp45api.repository.InstrumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepo;

    @Autowired
    private CategoriaInstrumentoRepository categoriaInstrumentoRepo;
    @Autowired
    private CategoriaInstrumentoService categoriaInstrumentoService;

    public void saveInstrumento(InstrumentoDTO instrumentoDTO) {
        Instrumento instrumento = toEntity(instrumentoDTO);
        instrumentoRepo.save(instrumento);
    }

    public InstrumentoDTO getInstrumentoById(Long id) {
        Instrumento instrumento = instrumentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrumento con el id " + id + " no encontrado"));
        return toDTO(instrumento);
    }

    public List<InstrumentoDTO> getAllInstrumentos() {
        List<Instrumento> instrumentos = instrumentoRepo.findAll();
        List<InstrumentoDTO> instrumentoDTOS = new ArrayList<>();

        for (Instrumento instrumento : instrumentos) {
            instrumentoDTOS.add(toDTO(instrumento));
        }
        return instrumentoDTOS;
    }

    public void updateInstrumento(Long id, InstrumentoDTO instrumentoDTO) {
        Instrumento instrumento = instrumentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrumento con el id " + id + " no encontrado"));

        if (!instrumento.getNombre().equals(instrumentoDTO.getNombre())) {
            instrumento.setNombre(instrumentoDTO.getNombre());
        }

        if (!instrumento.getMarca().equals(instrumentoDTO.getMarca())) {
            instrumento.setMarca(instrumentoDTO.getMarca());
        }

        if (!instrumento.getModelo().equals(instrumentoDTO.getModelo())) {
            instrumento.setModelo(instrumentoDTO.getModelo());
        }

        if (!instrumento.getDescripcion().equals(instrumentoDTO.getDescripcion())) {
            instrumento.setDescripcion(instrumentoDTO.getDescripcion());
        }

        if (!instrumento.getImagen().equals(instrumentoDTO.getImagen())) {
            instrumento.setImagen(instrumentoDTO.getImagen());
        }

        if (!instrumento.getPrecio().equals(instrumentoDTO.getPrecio())) {
            instrumento.setPrecio(instrumentoDTO.getPrecio());
        }

        if (!instrumento.getCostoEnvio().equals(instrumentoDTO.getCostoEnvio())) {
            instrumento.setCostoEnvio(instrumentoDTO.getCostoEnvio());
        }

        if (instrumento.getCantidadVendida() != instrumentoDTO.getCantidadVendida()) {
            instrumento.setCantidadVendida(instrumentoDTO.getCantidadVendida());
        }

        if (!instrumento.getActivo().equals(instrumentoDTO.getActivo())) {
            instrumento.setActivo(instrumentoDTO.getActivo());
        }

        if (!instrumento.getCategoria().getId().equals(instrumentoDTO.getCategoria().getId())) {
            instrumento.setCategoria(categoriaInstrumentoRepo.findById(instrumentoDTO.getCategoria().getId())
                    .orElseThrow(() -> new NotFoundException("Cagegoria con el id " + instrumentoDTO.getCategoria().getId() + " no encontrado")));
        }

        instrumentoRepo.save(instrumento);
    }

    public void deleteInstrumentoById(Long id) {
        Instrumento instrumento = instrumentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrumento con el id " + id + " no encontrado"));
        instrumento.setActivo(false);
        instrumentoRepo.save(instrumento);
    }

    // MAPPERS
    private Instrumento toEntity(InstrumentoDTO instrumentoDTO) {
        return Instrumento.builder()
                .nombre(instrumentoDTO.getNombre())
                .marca(instrumentoDTO.getMarca())
                .modelo(instrumentoDTO.getModelo())
                .descripcion(instrumentoDTO.getDescripcion())
                .imagen(instrumentoDTO.getImagen())
                .precio(instrumentoDTO.getPrecio())
                .costoEnvio(instrumentoDTO.getCostoEnvio())
                .cantidadVendida(instrumentoDTO.getCantidadVendida())
                .activo(instrumentoDTO.getActivo())
                .categoria(categoriaInstrumentoRepo.findById(instrumentoDTO.getCategoria().getId())
                        .orElseThrow(() -> new NotFoundException("Cagegoria con el id " + instrumentoDTO.getCategoria().getId() + " no encontrado")))
                .build();
    }

    private InstrumentoDTO toDTO(Instrumento instrumento) {
        return InstrumentoDTO.builder()
                .id(instrumento.getId())
                .nombre(instrumento.getNombre())
                .marca(instrumento.getMarca())
                .modelo(instrumento.getModelo())
                .descripcion(instrumento.getDescripcion())
                .imagen(instrumento.getImagen())
                .precio(instrumento.getPrecio())
                .costoEnvio(instrumento.getCostoEnvio())
                .cantidadVendida(instrumento.getCantidadVendida())
                .activo(instrumento.getActivo())
                .categoria(categoriaInstrumentoService.toDTO(instrumento.getCategoria()))
                .build();
    }

}
