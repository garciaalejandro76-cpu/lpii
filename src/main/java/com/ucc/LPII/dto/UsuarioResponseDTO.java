package com.ucc.LPII.dto;

public record UsuarioResponseDTO(
    Long id,
    String nombre,
    String correo,
    int edad,
    String rol
) {}