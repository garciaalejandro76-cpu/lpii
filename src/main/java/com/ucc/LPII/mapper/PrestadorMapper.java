package com.ucc.LPII.mapper;

import com.ucc.LPII.dto.PrestadorRequestDTO;
import com.ucc.LPII.dto.PrestadorResponseDTO;
import com.ucc.LPII.entity.Prestador;
import com.ucc.LPII.entity.Ubicacion;
import com.ucc.LPII.entity.Zona;
import org.springframework.stereotype.Component;

@Component
public class PrestadorMapper {

    private final ZonaMapper zonaMapper;
    private final UbicacionMapper ubicacionMapper;

    public PrestadorMapper(ZonaMapper zonaMapper, UbicacionMapper ubicacionMapper) {
        this.zonaMapper = zonaMapper;
        this.ubicacionMapper = ubicacionMapper;
    }

    public PrestadorResponseDTO entityToDTO(Prestador prestador) {
        return new PrestadorResponseDTO(
                prestador.getId(),
                prestador.getNombre(),
                prestador.getTipo() != null ? prestador.getTipo().name() : null,
                prestador.getCategoria(),
                prestador.getTelefono(),
                prestador.getDescripcion(),
                zonaMapper.entityToDTO(prestador.getZona()),
                ubicacionMapper.entityToDTO(prestador.getUbicacion())
        );
    }

    public Prestador dtoToEntity(PrestadorRequestDTO dto, Zona zona) {
        Prestador prestador = new Prestador();
        prestador.setNombre(dto.nombre());
        
        if (dto.tipo() != null) {
            prestador.setTipo(Prestador.TipoPrestador.valueOf(dto.tipo()));
        }
        
        prestador.setCategoria(dto.categoria());
        prestador.setTelefono(dto.telefono());
        prestador.setDescripcion(dto.descripcion());
        prestador.setZona(zona);

        if (dto.ubicacion() != null) {
            Ubicacion ubicacion = ubicacionMapper.requestDtoToEntity(dto.ubicacion());
            prestador.setUbicacion(ubicacion);
        }

        return prestador;
    }
}