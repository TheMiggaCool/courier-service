package com.courier.service.dto;

import java.util.UUID;

import com.courier.service.enums.EstadoCourier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta de GET /couriers/{id}/disponibilidad. Es lo que consume
 * shipment-service antes de asignar un repartidor a un envío.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadResponse {

    private UUID id;
    /** true si el courier está ACTIVO y por lo tanto puede recibir asignaciones. */
    private boolean disponible;
    private EstadoCourier estado;
}
