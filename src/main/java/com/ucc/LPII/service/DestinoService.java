package com.ucc.LPII.service;

import com.ucc.LPII.dto.DestinoRequestDTO;
import com.ucc.LPII.dto.DestinoResponseDTO;
import com.ucc.LPII.entity.Destino;
import com.ucc.LPII.exception.DestinoNotFoundException;
import com.ucc.LPII.mapper.DestinoMapper;
import com.ucc.LPII.repository.DestinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DestinoService {

    private final DestinoMapper destinoMapper;
    private final DestinoRepository destinoRepository;

    public DestinoService(DestinoMapper destinoMapper, DestinoRepository destinoRepository) {
        this.destinoMapper = destinoMapper;
        this.destinoRepository = destinoRepository;
    }

    public List<DestinoResponseDTO> obtenerTodosLosDestinos() {
        return destinoRepository.findAll()
                .stream()
                .map(destinoMapper::entityToDTO)
                .toList();
    }

    public DestinoResponseDTO obtenerUnDestino(Long id) {
        Destino destino = destinoRepository.findById(id).orElseThrow(() -> new DestinoNotFoundException(id));
        return destinoMapper.entityToDTO(destino);
    }

    public DestinoResponseDTO agregarDestino(DestinoRequestDTO dto) {
        Destino destino = destinoMapper.dtoToEntity(dto);
        Destino destinoGuardado = destinoRepository.save(destino);
        return destinoMapper.entityToDTO(destinoGuardado);
    }

    public DestinoResponseDTO modificarDestino(Long id, DestinoRequestDTO dto) {
        Optional<Destino> destinoOpt = destinoRepository.findById(id);

        if (destinoOpt.isEmpty()) {
            throw new DestinoNotFoundException(id);
        }

        Destino destinoModificado = destinoOpt.get();
        destinoModificado.setNombre(dto.nombre());
        destinoModificado.setDescripcion(dto.descripcion());
        destinoModificado.setPais(dto.pais());

        Destino destinoGuardado = destinoRepository.save(destinoModificado);
        return destinoMapper.entityToDTO(destinoGuardado);
    }

    public void borrarUnDestino(Long id) {
        destinoRepository.deleteById(id);
    }
}