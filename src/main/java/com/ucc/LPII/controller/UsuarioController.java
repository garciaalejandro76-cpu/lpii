package com.ucc.LPII.controller;

import com.ucc.LPII.dto.UsuarioRequestDTO;
import com.ucc.LPII.dto.UsuarioResponseDTO;
import com.ucc.LPII.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint público para el registro de nuevos usuarios
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // HTTP 201 Created
    }

    // Agrega este método dentro de tu UsuarioController existente:
    @GetMapping
    public org.springframework.http.ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return org.springframework.http.ResponseEntity.ok(usuarios); // HTTP 200 OK con la lista
    }
}