package com.atosalves.park_api.web.dto.mapper;

import org.springframework.beans.BeanUtils;

import com.atosalves.park_api.entity.ParkingSpot;
import com.atosalves.park_api.web.dto.parkingSpot.ParkingSpotCreateDto;
import com.atosalves.park_api.web.dto.parkingSpot.ParkingSpotResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpotMapper {

        public static ParkingSpot toParkingSpot(ParkingSpotCreateDto createDto) {
                var parkingSpot = new ParkingSpot();
                BeanUtils.copyProperties(createDto, parkingSpot);
                return parkingSpot;
        }

        public static ParkingSpotResponseDto toDto(ParkingSpot parkingSpot) {
                return new ParkingSpotResponseDto(parkingSpot.getId(), parkingSpot.getCode(),
                                parkingSpot.getStatus().name());
        }

}
