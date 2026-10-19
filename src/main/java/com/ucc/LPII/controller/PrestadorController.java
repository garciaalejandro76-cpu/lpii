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
// http://localhost:8080/api/prestadores

public class PrestadorController {

    private final PrestadorService prestadorService;

    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    //get, post, put, delete

    //2xx: salio bien por ej 200, 201
    //4xx: el cliente pidio algo mal ej: 400, 404
    //5xx: error del servidor ej: 500, 503
    
    // obtener todos
    @GetMapping()
    public ResponseEntity<List<PrestadorResponseDTO>> obtenerTodosLosPrestadores() {
        return ResponseEntity.ok(prestadorService.obtenerTodosLosPrestadores());
    }

    // obtener uno por id
    @GetMapping("/{id}")
    public ResponseEntity<PrestadorResponseDTO> obtenerUnPrestador(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prestadorService.obtenerUnPrestador(id));
    }

    // obtener prestadores por idZona
    @GetMapping("/zona/{idZona}")
    public ResponseEntity<List<PrestadorResponseDTO>> obtenerPrestadoresByZona(
            @PathVariable("idZona") Long idZona) {
        List<PrestadorResponseDTO> prestadores = prestadorService.obtenerPrestadoresByZona(idZona);
        return ResponseEntity.ok(prestadores);
    }

    // agregar un prestador
    @PostMapping()
    public ResponseEntity<PrestadorResponseDTO> agregarPrestador(@Valid @RequestBody PrestadorRequestDTO dto) {
        PrestadorResponseDTO creado = prestadorService.agregarPrestador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // modificar un prestador
    @PutMapping("/{id}")
    public ResponseEntity<PrestadorResponseDTO> modificarPrestador(@PathVariable("id") Long id, @RequestBody PrestadorRequestDTO dto) {
        PrestadorResponseDTO modificado = prestadorService.modificarPrestador(id, dto);
        if (modificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificado);
    }

    // eliminar un prestador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestador(@PathVariable("id") Long id) {
        prestadorService.borrarUnPrestador(id);
        return ResponseEntity.noContent().build();
    }
}