package com.courier.service.exceptions;

public class TransicionEstadoInvalidaException extends RuntimeException {
    public TransicionEstadoInvalidaException(String message) {
        super(message);
    }
}
