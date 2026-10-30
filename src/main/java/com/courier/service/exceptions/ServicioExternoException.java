package com.courier.service.exceptions;

/** Un servicio del que dependemos (security-service) no respondió como se esperaba (se responde 503). */
public class ServicioExternoException extends RuntimeException {
    public ServicioExternoException(String message) {
        super(message);
    }
}
