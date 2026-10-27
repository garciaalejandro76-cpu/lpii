package com.ucc.LPII.exception;

import com.ucc.LPII.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice // Escucha todas las excepciones de todos los controladores
public class GlobalExceptionHandler {



    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarUsuarioNoEncontrado(UsuarioNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(404, ex.getMessage(), java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(LugarNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarLugarNoEncontrado(LugarNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(404, ex.getMessage(), java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PrestadorNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarPrestadorNoEncontrado(PrestadorNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(404, ex.getMessage(), java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ZonaNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarZonaNoEncontrada(ZonaNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(404, ex.getMessage(), java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DestinoNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarDestinoNoEncontrado(DestinoNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(404, ex.getMessage(), java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // --- NUEVA EXCEPCIÓN: CONTROL DE CORREO REPETIDO (HTTP 409) ---
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> manejarEmailYaExistente(EmailAlreadyExistsException ex) {
        ErrorDTO error = new ErrorDTO(409, ex.getMessage(), java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // --- NUEVA EXCEPCIÓN: CONTROL DE CAMPOS INVÁLIDOS / @Valid (HTTP 400) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacionesDeCampos(MethodArgumentNotValidException ex) {
        // Concatenamos todos los errores de validación de propiedades en un solo string comprensible
        String detallesError = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErrorDTO error = new ErrorDTO(400, "Campos inválidos: " + detallesError, java.time.LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}