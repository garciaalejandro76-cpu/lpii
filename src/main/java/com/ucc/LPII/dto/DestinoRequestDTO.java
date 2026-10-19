package com.ucc.LPII.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DestinoRequestDTO(
        @NotBlank(message = "El nombre del destino es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        String descripcion,

        @Size(max = 100, message = "El nombre del país no puede superar los 100 caracteres")
        String pais
) {
}