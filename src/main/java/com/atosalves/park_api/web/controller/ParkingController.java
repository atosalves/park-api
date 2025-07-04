package com.atosalves.park_api.web.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.atosalves.park_api.service.ParkingService;
import com.atosalves.park_api.web.dto.mapper.CustomerParkingSpotMapper;
import com.atosalves.park_api.web.dto.parking.ParkingCreateDto;
import com.atosalves.park_api.web.dto.parking.ParkingResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkings")
public class ParkingController {

        private final ParkingService parkingService;

        @PostMapping("/check-in")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ParkingResponseDto> checkIn(@Valid @RequestBody ParkingCreateDto createDto) {
                var customerParkingSpot = CustomerParkingSpotMapper.toCustomerParkingSpot(createDto);
                parkingService.checkIn(customerParkingSpot);

                var response = CustomerParkingSpotMapper.toDto(customerParkingSpot);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .path("/{receipt}")
                                .buildAndExpand(customerParkingSpot.getReceipt())
                                .toUri();

                return ResponseEntity.created(location).body(response);
        }

}
