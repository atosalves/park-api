package com.atosalves.park_api.web.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.atosalves.park_api.entity.CustomerParkingSpot;
import com.atosalves.park_api.service.CustomerParkingSpotService;
import com.atosalves.park_api.service.ParkingService;
import com.atosalves.park_api.web.dto.PageableDto;
import com.atosalves.park_api.web.dto.mapper.CustomerParkingSpotMapper;
import com.atosalves.park_api.web.dto.mapper.PageableMapper;
import com.atosalves.park_api.web.dto.parking.ParkingCreateDto;
import com.atosalves.park_api.web.dto.parking.ParkingResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkings")
public class ParkingController {

        private final ParkingService parkingService;
        private final CustomerParkingSpotService customerParkingSpotService;

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

        @PutMapping("/check-out/{receipt}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ParkingResponseDto> checkOut(@PathVariable String receipt) {
                var customerParkingSpot = parkingService.checkOut(receipt);
                return ResponseEntity.ok().body(CustomerParkingSpotMapper.toDto(customerParkingSpot));
        }

        @GetMapping("/check-in/{receipt}")
        @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
        public ResponseEntity<ParkingResponseDto> getByReceipt(@PathVariable String receipt) {
                var customerParkingSpot = customerParkingSpotService.getByReceipt(receipt);
                return ResponseEntity.ok().body(CustomerParkingSpotMapper.toDto(customerParkingSpot));
        }

        @GetMapping("/cpf/{cpf}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDto<CustomerParkingSpot>> getAllByCpf(@PathVariable String cpf,
                        @PageableDefault(size = 5, sort = "entryAt", direction = Sort.Direction.ASC) Pageable pageable) {
                var projection = customerParkingSpotService.getAllByCpf(cpf, pageable);

                return ResponseEntity.ok(PageableMapper.toDto(projection));
        }

}
