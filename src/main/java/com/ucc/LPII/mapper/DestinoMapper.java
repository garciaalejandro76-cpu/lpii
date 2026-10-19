package com.ucc.LPII.mapper;

import com.ucc.LPII.dto.DestinoDTO;
import com.ucc.LPII.dto.DestinoRequestDTO;
import com.ucc.LPII.dto.DestinoResponseDTO;
import com.ucc.LPII.entity.Destino;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DestinoMapper {

    private final ZonaMapper zonaMapper;

    public DestinoMapper(ZonaMapper zonaMapper) {
        this.zonaMapper = zonaMapper;
    }

    public DestinoResponseDTO entityToDTO(Destino destino) {
        if (destino == null) return null;
        
        return new DestinoResponseDTO(
                destino.getId(),
                destino.getNombre(),
                destino.getDescripcion(),
                destino.getPais(),
                destino.getZonas() != null 
                        ? destino.getZonas().stream().map(zonaMapper::entityToDTO).toList() 
                        : new ArrayList<>()
        );
    }

    public Destino dtoToEntity(DestinoRequestDTO dto) {
        if (dto == null) return null;
        
        Destino destino = new Destino();
        destino.setNombre(dto.nombre());
        destino.setDescripcion(dto.descripcion());
        destino.setPais(dto.pais());
        return destino;
    }
    
    // Método auxiliar simple para evitar recursividad en ZonaResponseDTO
    public DestinoDTO entityToSimpleDTO(Destino destino) {
        if (destino == null) return null;
        return new DestinoDTO(destino.getId(), destino.getNombre(), destino.getPais());
    }
}