package com.ucc.LPII.controller;

import com.ucc.LPII.dto.LugarRequestDTO;
import com.ucc.LPII.dto.LugarResponseDTO;
import com.ucc.LPII.service.LugarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
// http://localhost:8080/api/lugares

public class LugarController {

    private final LugarService lugarService;

    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    //get, post, put, delete

    //2xx: salio bien por ej 200, 201
    //4xx: el cliente pidio algo mal ej: 400, 404
    //5xx: error del servidor ej: 500, 503
    
    // obtener todos
    @GetMapping()
    public ResponseEntity<List<LugarResponseDTO>> obtenerTodosLosLugares() {
        return ResponseEntity.ok(lugarService.obtenerTodosLosLugares());
    }

    // obtener uno por id
    @GetMapping("/{id}")
    public ResponseEntity<LugarResponseDTO> obtenerUnLugar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lugarService.obtenerUnLugar(id));
    }

    // obtener lugares por idZona (Siguiendo la lógica de tu ejemplo de idUsuario)
    @GetMapping("/zona/{idZona}")
    public ResponseEntity<List<LugarResponseDTO>> obtenerLugaresByZona(
            @PathVariable("idZona") Long idZona) {
        List<LugarResponseDTO> lugares = lugarService.obtenerLugaresByZona(idZona);
        return ResponseEntity.ok(lugares);
    }

    // agregar un lugar
    @PostMapping()
    public ResponseEntity<LugarResponseDTO> agregarLugar(@Valid @RequestBody LugarRequestDTO dto) {
        LugarResponseDTO creado = lugarService.agregarLugar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // modificar un lugar
    @PutMapping("/{id}")
    public ResponseEntity<LugarResponseDTO> modificarLugar(@PathVariable("id") Long id, @RequestBody LugarRequestDTO dto) {
        LugarResponseDTO modificado = lugarService.modificarLugar(id, dto);
        if (modificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificado);
    }

    // eliminar un lugar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLugar(@PathVariable("id") Long id) {
        lugarService.borrarUnLugar(id);
        return ResponseEntity.noContent().build();
    }
}