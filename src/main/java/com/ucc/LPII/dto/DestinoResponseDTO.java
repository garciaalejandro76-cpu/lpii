package com.ucc.LPII.dto;

import java.util.List;

public record DestinoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        String pais,
        List<ZonaDTO> zonas
) {
}