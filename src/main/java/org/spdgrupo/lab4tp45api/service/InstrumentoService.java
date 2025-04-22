package org.spdgrupo.lab4tp45api.service;

import org.spdgrupo.lab4tp45api.config.exception.NotFoundException;
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;
import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.spdgrupo.lab4tp45api.repository.InstrumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepo;

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
        List<InstrumentoDTO> instrumentoDTOs = new ArrayList<>();

        for (Instrumento instrumento : instrumentos) {
            instrumentoDTOs.add(toDTO(instrumento));
        }
        return instrumentoDTOs;
    }

    public void updateInstrumento(Long id, InstrumentoDTO instrumentoDTO) {
        Instrumento instrumento = instrumentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrumento con el id " + id + " no encontrado"));

        if (!instrumento.getInstrumento().equals(instrumentoDTO.getInstrumento())) {
            instrumento.setInstrumento(instrumentoDTO.getInstrumento());
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

        instrumentoRepo.save(instrumento);
    }

    // MAPPERS
    private Instrumento toEntity(InstrumentoDTO instrumentoDTO) {
        return Instrumento.builder()
                .instrumento(instrumentoDTO.getInstrumento())
                .marca(instrumentoDTO.getMarca())
                .modelo(instrumentoDTO.getModelo())
                .descripcion(instrumentoDTO.getDescripcion())
                .imagen(instrumentoDTO.getImagen())
                .precio(instrumentoDTO.getPrecio())
                .costoEnvio(instrumentoDTO.getCostoEnvio())
                .cantidadVendida(instrumentoDTO.getCantidadVendida())
                .build();
    }

    private InstrumentoDTO toDTO(Instrumento instrumento) {
        return InstrumentoDTO.builder()
                .id(instrumento.getId())
                .instrumento(instrumento.getInstrumento())
                .marca(instrumento.getMarca())
                .modelo(instrumento.getModelo())
                .descripcion(instrumento.getDescripcion())
                .imagen(instrumento.getImagen())
                .precio(instrumento.getPrecio())
                .costoEnvio(instrumento.getCostoEnvio())
                .cantidadVendida(instrumento.getCantidadVendida())
                .build();
    }

}
