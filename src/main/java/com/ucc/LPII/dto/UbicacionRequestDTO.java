package com.ucc.LPII.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UbicacionRequestDTO(
        @NotNull(message = "La latitud es obligatoria")
        BigDecimal latitud,
        
        @NotNull(message = "La longitud es obligatoria")
        BigDecimal longitud,
        
        @Size(max = 255, message = "La referencia no puede superar los 255 caracteres")
        String referencia
) {}