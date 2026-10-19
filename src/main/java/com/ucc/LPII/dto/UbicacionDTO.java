package com.ucc.LPII.dto;

import java.math.BigDecimal;

public record UbicacionDTO(
        Long id,
        BigDecimal latitud,
        BigDecimal longitud,
        String referencia
) {}