package com.courier.service.model;

import java.util.UUID;

import com.courier.service.audit.AuditableEntity;
import com.courier.service.enums.EstadoCourier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Perfil de repartidor de LogiTrack.
 *
 * <p>El identificador del courier <b>es el {@code userId}</b> del usuario con rol
 * REPARTIDOR registrado en security-service. Por eso el id NO se autogenera:
 * lo asigna el alta (ver {@code CourierRequest#userId}).</p>
 *
 * <p>No tiene relaciones JPA hacia envíos ni clientes: esos datos viven en
 * shipment-service, que referencia al courier solo por su id.</p>
 *
 * @author TheMiggaCool
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courier")
public class Courier extends AuditableEntity {

    /** Igual al userId de security-service. Asignado, nunca generado. */
    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(nullable = false, length = 30)
    private String tipoVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCourier estado;
}
