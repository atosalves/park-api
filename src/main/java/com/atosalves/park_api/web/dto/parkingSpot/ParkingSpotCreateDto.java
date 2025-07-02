package com.atosalves.park_api.web.dto.parkingSpot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ParkingSpotCreateDto(
                @NotBlank(message = "O código não deve estar vazio") @Size(min = 4, max = 4) String code,
                @NotBlank(message = "O status não deve estar vazio") @Pattern(regexp = "AVAILABLE|OCCUPIED") String status) {

}
