package com.ucc.LPII.dto;

public record ZonaResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        DestinoDTO destino
) {
}