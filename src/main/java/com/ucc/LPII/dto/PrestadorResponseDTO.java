package com.ucc.LPII.dto;

public record PrestadorResponseDTO(
        Long id,
        String nombre,
        String tipo,
        String categoria,
        String telefono,
        String descripcion,
        ZonaDTO zona,
        UbicacionDTO ubicacion
) {
}