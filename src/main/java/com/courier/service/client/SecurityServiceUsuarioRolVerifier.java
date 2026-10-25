package com.courier.service.client;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.courier.service.exceptions.ServicioExternoException;
import com.courier.service.exceptions.UsuarioInvalidoException;

import lombok.extern.slf4j.Slf4j;

/**
 * Consulta {@code GET {security-service}/users/{id}} reenviando el JWT del ADMIN que
 * está dando de alta al courier. Espera un JSON con al menos {@code roles: ["REPARTIDOR", ...]}.
 *
 * <p>Esta llamada es SOLO para el alta. La validación del JWT de cada request sigue
 * siendo local (clave pública), sin consultar a security-service.</p>
 */
@Slf4j
@Component
public class SecurityServiceUsuarioRolVerifier implements UsuarioRolVerifier {

    private static final String ROL_REPARTIDOR = "REPARTIDOR";

    private final RestClient restClient;

    public SecurityServiceUsuarioRolVerifier(RestClient.Builder builder,
            @Value("${logitrack.clients.security.base-url}") String baseUrl) {
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(3));
        this.restClient = builder.baseUrl(baseUrl).requestFactory(factory).build();
    }

    @Override
    public void verificarRepartidor(UUID userId) {
        UsuarioDto usuario;
        try {
            usuario = restClient.get()
                    .uri("/users/{id}", userId)
                    .headers(h -> tokenActual().ifPresent(h::setBearerAuth))
                    .retrieve()
                    .body(UsuarioDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UsuarioInvalidoException("No existe un usuario con id " + userId);
        } catch (HttpClientErrorException e) {
            log.warn("security-service rechazó la consulta del usuario {}: HTTP {}", userId, e.getStatusCode().value());
            throw new ServicioExternoException("security-service rechazó la consulta del usuario (HTTP "
                    + e.getStatusCode().value() + ")");
        } catch (RestClientException e) {
            log.warn("No se pudo consultar security-service: {}", e.getMessage());
            throw new ServicioExternoException("No se pudo consultar a security-service");
        }

        boolean esRepartidor = usuario != null && usuario.roles() != null && usuario.roles().stream()
                .anyMatch(r -> ROL_REPARTIDOR.equals(r) || ("ROLE_" + ROL_REPARTIDOR).equals(r));
        if (!esRepartidor) {
            throw new UsuarioInvalidoException("El usuario " + userId + " no tiene el rol REPARTIDOR");
        }
    }

    private static Optional<String> tokenActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof JwtAuthenticationToken jwt
                ? Optional.of(jwt.getToken().getTokenValue())
                : Optional.empty();
    }

    /** Subconjunto de la respuesta de security-service que nos interesa. */
    record UsuarioDto(UUID id, List<String> roles) {
    }
}
