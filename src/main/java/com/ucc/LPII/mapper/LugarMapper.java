package com.ucc.LPII.mapper;

import com.ucc.LPII.dto.LugarRequestDTO;
import com.ucc.LPII.dto.LugarResponseDTO;
import com.ucc.LPII.entity.Lugar;
import com.ucc.LPII.entity.Ubicacion;
import com.ucc.LPII.entity.Zona;
import org.springframework.stereotype.Component;

@Component
public class LugarMapper {

    private final ZonaMapper zonaMapper;
    private final UbicacionMapper ubicacionMapper;

    public LugarMapper(ZonaMapper zonaMapper, UbicacionMapper ubicacionMapper) {
        this.zonaMapper = zonaMapper;
        this.ubicacionMapper = ubicacionMapper;
    }

    public LugarResponseDTO entityToDTO(Lugar lugar) {
        return new LugarResponseDTO(
                lugar.getId(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                zonaMapper.entityToDTO(lugar.getZona()),
                ubicacionMapper.entityToDTO(lugar.getUbicacion())
        );
    }

    public Lugar dtoToEntity(LugarRequestDTO dto, Zona zona) {
        Lugar lugar = new Lugar();
        lugar.setNombre(dto.nombre());
        lugar.setDescripcion(dto.descripcion());
        lugar.setZona(zona);
        
        // Mapea la ubicación satélite embebida en el Request
        if (dto.ubicacion() != null) {
            Ubicacion ubicacion = ubicacionMapper.requestDtoToEntity(dto.ubicacion());
            lugar.setUbicacion(ubicacion);
        }
        
        return lugar;
    }
}