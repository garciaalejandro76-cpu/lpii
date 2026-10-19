package com.ucc.LPII.exception;

public class DestinoNotFoundException extends RuntimeException {
    public DestinoNotFoundException(Long id) { super("No existe el destino con id " + id); }
}
