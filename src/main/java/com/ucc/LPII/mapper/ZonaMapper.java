package com.ucc.LPII.mapper;

import com.ucc.LPII.dto.ZonaDTO;
import com.ucc.LPII.dto.ZonaRequestDTO;
import com.ucc.LPII.dto.ZonaResponseDTO;
import com.ucc.LPII.entity.Destino;
import com.ucc.LPII.entity.Zona;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ZonaMapper {

    private final DestinoMapper destinoMapper;

    // Se usa @Lazy para evitar una dependencia circular entre DestinoMapper y ZonaMapper
    public ZonaMapper(@Lazy DestinoMapper destinoMapper) {
        this.destinoMapper = destinoMapper;
    }

    // Mapeo simple (el que usan Lugar y Prestador)
    public ZonaDTO entityToDTO(Zona zona) {
        if (zona == null) return null;
        return new ZonaDTO(zona.getId(), zona.getNombre(), zona.getDescripcion());
    }

    // Mapeo completo solicitado por tu nuevo ZonaService
    public ZonaResponseDTO entityToResponseDTO(Zona zona) {
        if (zona == null) return null;
        return new ZonaResponseDTO(
                zona.getId(),
                zona.getNombre(),
                zona.getDescripcion(),
                destinoMapper.entityToSimpleDTO(zona.getDestino())
        );
    }

    public Zona dtoToEntity(ZonaRequestDTO dto, Destino destino) {
        if (dto == null) return null;
        Zona zona = new Zona();
        zona.setNombre(dto.nombre());
        zona.setDescripcion(dto.descripcion());
        zona.setDestino(destino);
        return zona;
    }
}