package com.ucc.LPII.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("El correo electrónico '" + email + "' ya se encuentra registrado.");
    }
}