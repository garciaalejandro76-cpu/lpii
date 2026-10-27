package com.ucc.LPII.controller;

import com.ucc.LPII.dto.DestinoRequestDTO;
import com.ucc.LPII.dto.DestinoResponseDTO;
import com.ucc.LPII.service.DestinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    private final DestinoService destinoService;

    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    @GetMapping()
    public ResponseEntity<List<DestinoResponseDTO>> obtenerTodosLosDestinos() {
        return ResponseEntity.ok(destinoService.obtenerTodosLosDestinos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinoResponseDTO> obtenerUnDestino(@PathVariable("id") Long id) {
        return ResponseEntity.ok(destinoService.obtenerUnDestino(id));
    }

    @PostMapping()
    public ResponseEntity<DestinoResponseDTO> agregarDestino(@Valid @RequestBody DestinoRequestDTO dto) {
        DestinoResponseDTO creado = destinoService.agregarDestino(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // CORRECCIÓN: Se añade @Valid para validar el DTO de entrada en la modificación
    @PutMapping("/{id}")
    public ResponseEntity<DestinoResponseDTO> modificarDestino(@PathVariable("id") Long id, @Valid @RequestBody DestinoRequestDTO dto) {
        DestinoResponseDTO modificado = destinoService.modificarDestino(id, dto);
        if (modificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDestino(@PathVariable("id") Long id) {
        destinoService.borrarUnDestino(id);
        return ResponseEntity.noContent().build();
    }
}