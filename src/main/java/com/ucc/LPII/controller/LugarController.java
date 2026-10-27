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
public class LugarController {

    private final LugarService lugarService;

    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    @GetMapping()
    public ResponseEntity<List<LugarResponseDTO>> obtenerTodosLosLugares() {
        return ResponseEntity.ok(lugarService.obtenerTodosLosLugares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LugarResponseDTO> obtenerUnLugar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lugarService.obtenerUnLugar(id));
    }

    @GetMapping("/zona/{idZona}")
    public ResponseEntity<List<LugarResponseDTO>> obtenerLugaresByZona(@PathVariable("idZona") Long idZona) {
        List<LugarResponseDTO> lugares = lugarService.obtenerLugaresByZona(idZona);
        return ResponseEntity.ok(lugares);
    }

    @PostMapping()
    public ResponseEntity<LugarResponseDTO> agregarLugar(@Valid @RequestBody LugarRequestDTO dto) {
        LugarResponseDTO creado = lugarService.agregarLugar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // CORRECCIÓN: Se añade @Valid para validar el DTO de entrada en la modificación
    @PutMapping("/{id}")
    public ResponseEntity<LugarResponseDTO> modificarLugar(@PathVariable("id") Long id, @Valid @RequestBody LugarRequestDTO dto) {
        LugarResponseDTO modificado = lugarService.modificarLugar(id, dto);
        if (modificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLugar(@PathVariable("id") Long id) {
        lugarService.borrarUnLugar(id);
        return ResponseEntity.noContent().build();
    }
}