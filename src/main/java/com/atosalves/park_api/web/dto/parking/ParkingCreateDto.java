package com.atosalves.park_api.web.dto.parking;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ParkingCreateDto(

                @NotBlank @CPF @Size(max = 11) String customerCpf,

                @NotBlank @Size(min = 8, max = 8) @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "A placa do veículo deve seguir o padrão 'XXX-0000") String licensePlate,

                @NotBlank String brand,

                @NotBlank String model,

                String color

) {

}
