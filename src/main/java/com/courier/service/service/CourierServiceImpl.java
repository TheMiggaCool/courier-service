package com.courier.service.service;

import java.util.List;
import java.util.UUID;

import com.courier.service.dto.CourierRequest;
import com.courier.service.dto.CourierResponse;
import com.courier.service.enums.EstadoCourier;
import com.courier.service.exceptions.RecursoNoEncontradoException;
import com.courier.service.model.Courier;
import com.courier.service.repository.CourierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    @Transactional
    public CourierResponse create(CourierRequest CourierRequest) {
        Courier Courier = mapToEntity(CourierRequest);
        Courier savedCourier = courierRepository.save(Courier);
        log.info("Se ha creado el Courier: " + savedCourier.getId());

        return mapToResponseDto(savedCourier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourierResponse> findAll(EstadoCourier estadoCourier) {
        List<Courier> Courieres = null;
        if (estadoCourier == null) {
            Courieres = courierRepository.findAll();
            log.info("Se han encontraron " + Courieres.size() + " Courieres");
        } else {
            Courieres = courierRepository.findByEstadoCourier(estadoCourier);
            log.info("Se han encontraron " + Courieres.size() + " Courieres: " + estadoCourier);
        }
        return Courieres
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourierResponse findById(UUID id) {
        Courier Courier = courierRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontro el Courier: " + id);
                    return new RecursoNoEncontradoException("Courier no encontrado: " + id);
                });
        log.info("Se ha encontro el Courier: " + id);
        return mapToResponseDto(Courier);
    }

    private CourierResponse mapToResponseDto(Courier courier) {
        return CourierResponse.builder()
                .id(courier.getId())
                .nombre(courier.getNombre())
                .tipoVehiculo(courier.getTipoVehiculo())
                .estadoCourier(courier.getEstadoCourier())
                .build();
    }

    private Courier mapToEntity(CourierRequest courierRequest) {
        Courier courier = Courier.builder()
                .nombre(courierRequest.getNombre())
                .tipoVehiculo(courierRequest.getTipoVehiculo())
                .estadoCourier(EstadoCourier.ACTIVO)
                .build();
        return courier;
    }

}