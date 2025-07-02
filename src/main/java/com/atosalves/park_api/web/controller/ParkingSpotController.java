package com.atosalves.park_api.web.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.atosalves.park_api.service.ParkingSpotService;
import com.atosalves.park_api.web.dto.mapper.ParkingSpotMapper;
import com.atosalves.park_api.web.dto.parkingSpot.ParkingSpotCreateDto;
import com.atosalves.park_api.web.dto.parkingSpot.ParkingSpotResponseDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parking-spots")
public class ParkingSpotController {

        private final ParkingSpotService parkingSpotService;

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Void> create(@Valid @RequestBody ParkingSpotCreateDto createDto) {
                var parkingSpot = ParkingSpotMapper.toParkingSpot(createDto);
                parkingSpotService.create(parkingSpot);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .path("/{code}")
                                .buildAndExpand(parkingSpot.getCode())
                                .toUri();

                return ResponseEntity.created(location).build();
        }

        @GetMapping("/{code}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ParkingSpotResponseDto> getByCode(@PathVariable String code) {
                var parkingSpot = parkingSpotService.getByCode(code);
                return ResponseEntity.ok(ParkingSpotMapper.toDto(parkingSpot));
        }

}
