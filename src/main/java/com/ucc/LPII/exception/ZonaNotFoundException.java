package com.ucc.LPII.exception;

public class ZonaNotFoundException extends RuntimeException {
    public ZonaNotFoundException(Long id) {
        super("No existe la zona con id " + id);
    }
}