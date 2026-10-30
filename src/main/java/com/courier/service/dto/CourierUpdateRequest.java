package com.courier.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos modificables de un courier (PUT /couriers/{id}).
 * El userId no se puede cambiar: es el id del recurso.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourierUpdateRequest {

    @NotBlank(message = "El nombre del repartidor es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El tipo de vehiculo del repartidor es obligatorio.")
    @Size(min = 2, max = 30, message = "El tipo de vehículo debe tener entre 2 y 30 caracteres.")
    private String tipoVehiculo;
}
