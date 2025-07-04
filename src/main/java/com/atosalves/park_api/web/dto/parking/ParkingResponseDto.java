package com.atosalves.park_api.web.dto.parking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParkingResponseDto(

                String custorerCpf,

                String receipt,

                String parkingSpotCode,

                @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime entryAt,

                @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime exitAt,

                BigDecimal price,

                BigDecimal descount,

                String licensePlate,

                String brand,

                String model,

                String color

) {

}
