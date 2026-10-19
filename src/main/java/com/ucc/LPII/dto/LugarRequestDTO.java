package com.ucc.LPII.dto;

import jakarta.validation.constraints.*;

public record LugarRequestDTO(
        @NotBlank(message = "El nombre del lugar es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        String descripcion,

        @NotNull(message = "El id de la zona es obligatorio")
        Long zonaId,

        @NotNull(message = "Las coordenadas de ubicación son obligatorias")
        UbicacionRequestDTO ubicacion
) {
}