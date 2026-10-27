package com.ucc.LPII.controller;

import com.ucc.LPII.dto.PrestadorRequestDTO;
import com.ucc.LPII.dto.PrestadorResponseDTO;
import com.ucc.LPII.service.PrestadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestadores")
public class PrestadorController {

    private final PrestadorService prestadorService;

    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @GetMapping()
    public ResponseEntity<List<PrestadorResponseDTO>> obtenerTodosLosPrestadores() {
        return ResponseEntity.ok(prestadorService.obtenerTodosLosPrestadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestadorResponseDTO> obtenerUnPrestador(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prestadorService.obtenerUnPrestador(id));
    }

    @GetMapping("/zona/{idZona}")
    public ResponseEntity<List<PrestadorResponseDTO>> obtenerPrestadoresByZona(@PathVariable("idZona") Long idZona) {
        List<PrestadorResponseDTO> prestadores = prestadorService.obtenerPrestadoresByZona(idZona);
        return ResponseEntity.ok(prestadores);
    }

    @PostMapping()
    public ResponseEntity<PrestadorResponseDTO> agregarPrestador(@Valid @RequestBody PrestadorRequestDTO dto) {
        PrestadorResponseDTO creado = prestadorService.agregarPrestador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // CORRECCIÓN: Se añade @Valid para validar el DTO de entrada en la modificación
    @PutMapping("/{id}")
    public ResponseEntity<PrestadorResponseDTO> modificarPrestador(@PathVariable("id") Long id, @Valid @RequestBody PrestadorRequestDTO dto) {
        PrestadorResponseDTO modificado = prestadorService.modificarPrestador(id, dto);
        if (modificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestador(@PathVariable("id") Long id) {
        prestadorService.borrarUnPrestador(id);
        return ResponseEntity.noContent().build();
    }
}