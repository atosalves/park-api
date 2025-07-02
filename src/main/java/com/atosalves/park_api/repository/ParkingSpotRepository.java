package com.atosalves.park_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atosalves.park_api.entity.ParkingSpot;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

        Optional<ParkingSpot> findByCode(String code);

}
