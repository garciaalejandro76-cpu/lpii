package com.ucc.LPII.exception;

public class LugarNotFoundException extends RuntimeException {
    public LugarNotFoundException(Long id) {
        super("No existe el lugar con id " + id);
    }
}