package com.courier.service.dto;

import java.util.UUID;

import com.courier.service.enums.EstadoCourier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourierResponse {

    private UUID id;
    private String nombre;
    private String tipoVehiculo;
    private EstadoCourier estado;

}
