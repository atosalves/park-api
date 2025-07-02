package com.atosalves.park_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atosalves.park_api.entity.ParkingSpot;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

}
