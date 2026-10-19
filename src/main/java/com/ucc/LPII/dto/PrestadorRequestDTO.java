package com.ucc.LPII.dto;

import jakarta.validation.constraints.*;

public record PrestadorRequestDTO(
        @NotBlank(message = "El nombre del prestador es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        @NotBlank(message = "El tipo de prestador (Hospedaje/Comercio) es obligatorio")
        String tipo,

        @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
        String categoria,

        @Size(max = 50, message = "El teléfono no puede superar los 50 caracteres")
        String telefono,

        String descripcion,

        @NotNull(message = "El id de la zona es obligatorio")
        Long zonaId,

        @NotNull(message = "Las coordenadas de ubicación son obligatorias")
        UbicacionRequestDTO ubicacion
) {
}