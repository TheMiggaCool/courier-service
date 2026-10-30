package com.courier.service.dto;

import com.courier.service.enums.EstadoCourier;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Cuerpo de PATCH /couriers/{id}/estado. */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourierEstadoRequest {

    @NotNull(message = "El estado es obligatorio.")
    private EstadoCourier estado;
}
