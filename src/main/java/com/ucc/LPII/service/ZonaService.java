package com.ucc.LPII.service;

import com.ucc.LPII.dto.ZonaRequestDTO;
import com.ucc.LPII.dto.ZonaResponseDTO;
import com.ucc.LPII.entity.Destino;
import com.ucc.LPII.entity.Zona;
import com.ucc.LPII.exception.DestinoNotFoundException;
import com.ucc.LPII.exception.ZonaNotFoundException;
import com.ucc.LPII.mapper.ZonaMapper;
import com.ucc.LPII.repository.DestinoRepository;
import com.ucc.LPII.repository.ZonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZonaService {

    private final ZonaMapper zonaMapper;
    private final ZonaRepository zonaRepository;
    private final DestinoRepository destinoRepository;

    public ZonaService(ZonaMapper zonaMapper, ZonaRepository zonaRepository, DestinoRepository destinoRepository) {
        this.zonaMapper = zonaMapper;
        this.zonaRepository = zonaRepository;
        this.destinoRepository = destinoRepository;
    }

    public List<ZonaResponseDTO> obtenerTodasLasZonas() {
        return zonaRepository.findAll()
                .stream()
                .map(zonaMapper::entityToResponseDTO)
                .toList();
    }

    public ZonaResponseDTO obtenerUnaZona(Long id) {
        Zona zona = zonaRepository.findById(id).orElseThrow(() -> new ZonaNotFoundException(id));
        return zonaMapper.entityToResponseDTO(zona);
    }

    public ZonaResponseDTO agregarZona(ZonaRequestDTO dto) {
        Destino destino = buscarDestinoById(dto.destinoId());
        Zona zona = zonaMapper.dtoToEntity(dto, destino);
        Zona zonaGuardada = zonaRepository.save(zona);
        return zonaMapper.entityToResponseDTO(zonaGuardada);
    }

    public ZonaResponseDTO modificarZona(Long id, ZonaRequestDTO dto) {
        Optional<Zona> zonaOpt = zonaRepository.findById(id);

        if (zonaOpt.isEmpty()) {
            throw new ZonaNotFoundException(id);
        }

        Zona zonaModificada = zonaOpt.get();
        zonaModificada.setNombre(dto.nombre());
        zonaModificada.setDescripcion(dto.descripcion());

        Destino destino = buscarDestinoById(dto.destinoId());
        zonaModificada.setDestino(destino);

        Zona zonaGuardada = zonaRepository.save(zonaModificada);
        return zonaMapper.entityToResponseDTO(zonaGuardada);
    }

    public void borrarUnaZona(Long id) {
        zonaRepository.deleteById(id);
    }

    public List<ZonaResponseDTO> obtenerZonasByDestino(Long idDestino) {
        buscarDestinoById(idDestino); // Lanza excepción si el destino no existe
        return zonaRepository.findByDestino_Id(idDestino)
                .stream()
                .map(zonaMapper::entityToResponseDTO)
                .toList();
    }

    private Destino buscarDestinoById(Long idDestino) {
        return destinoRepository.findById(idDestino)
                .orElseThrow(() -> new DestinoNotFoundException(idDestino));
    }
}