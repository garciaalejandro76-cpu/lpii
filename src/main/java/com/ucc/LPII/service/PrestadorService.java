package com.ucc.LPII.service;

import com.ucc.LPII.dto.PrestadorRequestDTO;
import com.ucc.LPII.dto.PrestadorResponseDTO;
import com.ucc.LPII.entity.Prestador;
import com.ucc.LPII.entity.Ubicacion;
import com.ucc.LPII.entity.Zona;
import com.ucc.LPII.exception.PrestadorNotFoundException;
import com.ucc.LPII.exception.ZonaNotFoundException;
import com.ucc.LPII.mapper.PrestadorMapper;
import com.ucc.LPII.repository.PrestadorRepository;
import com.ucc.LPII.repository.ZonaRepository;
import com.ucc.LPII.repository.UbicacionRepository; // 1. Importación del repositorio de ubicación
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrestadorService {

    private final PrestadorMapper prestadorMapper;
    private final PrestadorRepository prestadorRepository;
    private final ZonaRepository zonaRepository;
    private final UbicacionRepository ubicacionRepository; // 2. Dependencia añadida

    // 3. Constructor modificado para incluir la inyección de UbicacionRepository
    public PrestadorService(PrestadorMapper prestadorMapper, PrestadorRepository prestadorRepository, ZonaRepository zonaRepository, UbicacionRepository ubicacionRepository) {
        this.prestadorMapper = prestadorMapper;
        this.prestadorRepository = prestadorRepository;
        this.zonaRepository = zonaRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<PrestadorResponseDTO> obtenerTodosLosPrestadores() {
        return prestadorRepository.findAll()
                .stream()
                .map(prestadorMapper::entityToDTO)
                .toList();
    }

    public PrestadorResponseDTO obtenerUnPrestador(Long id) {
        Prestador prestador = prestadorRepository.findById(id).orElseThrow(() -> new PrestadorNotFoundException(id));
        return prestadorMapper.entityToDTO(prestador);
    }

    // 4. MÉTODO CORREGIDO: Guarda la ubicación antes de registrar el prestador
    public PrestadorResponseDTO agregarPrestador(PrestadorRequestDTO dto) {
        Zona zona = buscarZonaById(dto.zonaId());
        Prestador prestador = prestadorMapper.dtoToEntity(dto, zona);
        
        // Persistimos la ubicación primero para generar su ID único obligatorio
        if (prestador.getUbicacion() != null) {
            Ubicacion ubicacionGuardada = ubicacionRepository.save(prestador.getUbicacion());
            prestador.setUbicacion(ubicacionGuardada);
        }

        Prestador prestadorGuardado = prestadorRepository.save(prestador);
        return prestadorMapper.entityToDTO(prestadorGuardado);
    }

    public PrestadorResponseDTO modificarPrestador(Long id, PrestadorRequestDTO dto) {
        Optional<Prestador> prestadorOpt = prestadorRepository.findById(id);

        if (prestadorOpt.isEmpty()) {
            throw new PrestadorNotFoundException(id);
        }

        Prestador prestadorModificado = prestadorOpt.get();
        prestadorModificado.setNombre(dto.nombre());
        prestadorModificado.setCategoria(dto.categoria());
        prestadorModificado.setTelefono(dto.telefono());
        prestadorModificado.setDescripcion(dto.descripcion());
        
        if (dto.tipo() != null) {
            prestadorModificado.setTipo(Prestador.TipoPrestador.valueOf(dto.tipo()));
        }

        Zona zona = buscarZonaById(dto.zonaId());
        prestadorModificado.setZona(zona);

        // Actualizamos y sincronizamos la entidad de ubicación satélite vinculada
        if (dto.ubicacion() != null && prestadorModificado.getUbicacion() != null) {
            prestadorModificado.getUbicacion().setLatitud(dto.ubicacion().latitud());
            prestadorModificado.getUbicacion().setLongitud(dto.ubicacion().longitud());
            prestadorModificado.getUbicacion().setReferencia(dto.ubicacion().referencia());
            ubicacionRepository.save(prestadorModificado.getUbicacion());
        }

        Prestador prestadorGuardado = prestadorRepository.save(prestadorModificado);
        return prestadorMapper.entityToDTO(prestadorGuardado);
    }

    public void borrarUnPrestador(Long id) {
        prestadorRepository.deleteById(id);
    }

    public List<PrestadorResponseDTO> obtenerPrestadoresByZona(Long idZona) {
        Zona zona = buscarZonaById(idZona);
        return prestadorRepository.findByZona_Id(idZona)
                .stream()
                .map(prestadorMapper::entityToDTO)
                .toList();
    }

    private Zona buscarZonaById(Long idZona) {
        return zonaRepository.findById(idZona)
                .orElseThrow(() -> new ZonaNotFoundException(idZona));
    }
}