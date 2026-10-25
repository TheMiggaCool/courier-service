package com.courier.service.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos para el alta de un courier.
 *
 * @author TheMiggaCool
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourierRequest {

    /** Id del usuario (security-service) con rol REPARTIDOR; será el id del courier. */
    @NotNull(message = "El userId es obligatorio.")
    private UUID userId;

    @NotBlank(message = "El nombre del repartidor es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El tipo de vehiculo del repartidor es obligatorio.")
    @Size(min = 2, max = 30, message = "El tipo de vehículo debe tener entre 2 y 30 caracteres.")
    private String tipoVehiculo;
}
