package com.courier.service.controller;

import java.util.List;
import java.util.UUID;

import com.courier.service.dto.CourierRequest;
import com.courier.service.dto.CourierResponse;
import com.courier.service.enums.EstadoCourier;
import com.courier.service.model.Courier;
import com.courier.service.service.CourierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    public ResponseEntity<CourierResponse> create(@Valid @RequestBody CourierRequest repartidorRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courierService.create(repartidorRequest));
    }

    @GetMapping
    public ResponseEntity<List<CourierResponse>> findAll(@RequestParam(required = false) EstadoCourier estado) {
        return ResponseEntity.ok(courierService.findAll(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(courierService.findById(id));
    }



}
