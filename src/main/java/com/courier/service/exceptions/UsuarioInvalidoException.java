package com.courier.service.exceptions;

/** El userId no existe o no pertenece a un usuario con rol REPARTIDOR (se responde 400). */
public class UsuarioInvalidoException extends RuntimeException {
    public UsuarioInvalidoException(String message) {
        super(message);
    }
}
