package com.ucc.LPII.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ZonaRequestDTO(
        @NotBlank(message = "El nombre de la zona es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        String descripcion,

        @NotNull(message = "El id del destino asociado es obligatorio")
        Long destinoId
) {
}