package com.ucc.LPII.mapper;

import com.ucc.LPII.dto.UbicacionDTO;
import com.ucc.LPII.dto.UbicacionRequestDTO;
import com.ucc.LPII.entity.Ubicacion;
import org.springframework.stereotype.Component;

@Component
public class UbicacionMapper {

    public UbicacionDTO entityToDTO(Ubicacion ubicacion) {
        if (ubicacion == null) return null;
        return new UbicacionDTO(
                ubicacion.getId(),
                ubicacion.getLatitud(),
                ubicacion.getLongitud(),
                ubicacion.getReferencia()
        );
    }

    public Ubicacion requestDtoToEntity(UbicacionRequestDTO dto) {
        if (dto == null) return null;
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(dto.latitud());
        ubicacion.setLongitud(dto.longitud());
        ubicacion.setReferencia(dto.referencia());
        return ubicacion;
    }
}
