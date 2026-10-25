package com.courier.service.client;

import java.util.UUID;

/**
 * Verifica que un userId corresponda a un usuario existente con rol REPARTIDOR.
 *
 * <p>Es una interfaz a propósito: el mecanismo concreto (HTTP a security-service)
 * se acordó con el responsable de security-service, y los tests lo reemplazan por un mock
 * sin necesidad de levantar ese servicio.</p>
 */
public interface UsuarioRolVerifier {

    /**
     * @throws com.courier.service.exceptions.UsuarioInvalidoException si el usuario no existe o no es REPARTIDOR
     * @throws com.courier.service.exceptions.ServicioExternoException si security-service no responde
     */
    void verificarRepartidor(UUID userId);
}
