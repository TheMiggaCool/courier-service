package com.courier.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Habilita la auditoría de Spring Data JPA (createdDate / lastModifiedDate de
 * {@code AuditableEntity}). Sin esta clase esos campos quedan en null y el
 * INSERT falla por las columnas NOT NULL.
 * @author TheMiggaCool
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
