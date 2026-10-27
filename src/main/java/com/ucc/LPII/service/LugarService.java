package com.ucc.LPII.service;

import com.ucc.LPII.dto.LugarRequestDTO;
import com.ucc.LPII.dto.LugarResponseDTO;
import com.ucc.LPII.entity.Lugar;
import com.ucc.LPII.entity.Ubicacion;
import com.ucc.LPII.entity.Zona;
import com.ucc.LPII.exception.LugarNotFoundException;
import com.ucc.LPII.exception.ZonaNotFoundException;
import com.ucc.LPII.mapper.LugarMapper;
import com.ucc.LPII.repository.LugarRepository;
import com.ucc.LPII.repository.ZonaRepository;
import com.ucc.LPII.repository.UbicacionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LugarService {

    private final LugarMapper lugarMapper;
    private final LugarRepository lugarRepository;
    private final ZonaRepository zonaRepository;
    private final UbicacionRepository ubicacionRepository;

    public LugarService(LugarMapper lugarMapper, LugarRepository lugarRepository, ZonaRepository zonaRepository, UbicacionRepository ubicacionRepository) {
        this.lugarMapper = lugarMapper;
        this.lugarRepository = lugarRepository;
        this.zonaRepository = zonaRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<LugarResponseDTO> obtenerTodosLosLugares() {
        return lugarRepository.findAll()
                .stream()
                .map(lugarMapper::entityToDTO)
                .toList();
    }

    public LugarResponseDTO obtenerUnLugar(Long id) {
        Lugar lugar = lugarRepository.findById(id).orElseThrow(() -> new LugarNotFoundException(id));
        return lugarMapper.entityToDTO(lugar);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public LugarResponseDTO agregarLugar(LugarRequestDTO dto) {
        Zona zona = buscarZonaById(dto.zonaId());
        Lugar lugar = lugarMapper.dtoToEntity(dto, zona);
        
        if (lugar.getUbicacion() != null) {
            Ubicacion ubicacionGuardada = ubicacionRepository.save(lugar.getUbicacion());
            lugar.setUbicacion(ubicacionGuardada);
        }

        Lugar lugarGuardado = lugarRepository.save(lugar);
        return lugarMapper.entityToDTO(lugarGuardado);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public LugarResponseDTO modificarLugar(Long id, LugarRequestDTO dto) {
        Optional<Lugar> lugarOpt = lugarRepository.findById(id);

        if (lugarOpt.isEmpty()) {
            throw new LugarNotFoundException(id);
        }

        Lugar lugarModificado = lugarOpt.get();
        lugarModificado.setNombre(dto.nombre());
        lugarModificado.setDescripcion(dto.descripcion());

        Zona zona = buscarZonaById(dto.zonaId());
        lugarModificado.setZona(zona);
        
        if (dto.ubicacion() != null && lugarModificado.getUbicacion() != null) {
            lugarModificado.getUbicacion().setLatitud(dto.ubicacion().latitud());
            lugarModificado.getUbicacion().setLongitud(dto.ubicacion().longitud());
            lugarModificado.getUbicacion().setReferencia(dto.ubicacion().referencia());
            ubicacionRepository.save(lugarModificado.getUbicacion());
        }

        Lugar lugarGuardado = lugarRepository.save(lugarModificado);
        return lugarMapper.entityToDTO(lugarGuardado);
    }

    // CORRECCIÓN: Valida la existencia del recurso antes de invocar la baja en persistencia
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void borrarUnLugar(Long id) {
        if (!lugarRepository.existsById(id)) {
            throw new LugarNotFoundException(id);
        }
        lugarRepository.deleteById(id);
    }

    public List<LugarResponseDTO> obtenerLugaresByZona(Long idZona) {
        Zona zona = buscarZonaById(idZona);
        return lugarRepository.findByZona_Id(idZona)
                .stream()
                .map(lugarMapper::entityToDTO)
                .toList();
    }

    private Zona buscarZonaById(Long idZona) {
        return zonaRepository.findById(idZona)
                .orElseThrow(() -> new ZonaNotFoundException(idZona));
    }
}