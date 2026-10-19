package com.ucc.LPII.exception;

public class PrestadorNotFoundException extends RuntimeException {
    public PrestadorNotFoundException(Long id) {
        super("No existe el prestador con id " + id);
    }
}