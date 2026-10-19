package com.ucc.LPII.controller;

import com.ucc.LPII.dto.ZonaRequestDTO;
import com.ucc.LPII.dto.ZonaResponseDTO;
import com.ucc.LPII.service.ZonaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping()
    public ResponseEntity<List<ZonaResponseDTO>> obtenerTodasLasZonas() {
        return ResponseEntity.ok(zonaService.obtenerTodasLasZonas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaResponseDTO> obtenerUnaZona(@PathVariable("id") Long id) {
        return ResponseEntity.ok(zonaService.obtenerUnaZona(id));
    }

    // Obtener zonas pertenecientes a un destino específico (Llave foránea destino_id)
    @GetMapping("/destino/{idDestino}")
    public ResponseEntity<List<ZonaResponseDTO>> obtenerZonasByDestino(@PathVariable("idDestino") Long idDestino) {
        List<ZonaResponseDTO> zonas = zonaService.obtenerZonasByDestino(idDestino);
        return ResponseEntity.ok(zonas);
    }

    @PostMapping()
    public ResponseEntity<ZonaResponseDTO> agregarZona(@Valid @RequestBody ZonaRequestDTO dto) {
        ZonaResponseDTO creada = zonaService.agregarZona(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaResponseDTO> modificarZona(@PathVariable("id") Long id, @RequestBody ZonaRequestDTO dto) {
        ZonaResponseDTO modificada = zonaService.modificarZona(id, dto);
        if (modificada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarZona(@PathVariable("id") Long id) {
        zonaService.borrarUnaZona(id);
        return ResponseEntity.noContent().build();
    }
}