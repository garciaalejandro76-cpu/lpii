package com.ucc.LPII.dto;

public record LugarResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        ZonaDTO zona,
        UbicacionDTO ubicacion
) {
}