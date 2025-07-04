package com.atosalves.park_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atosalves.park_api.entity.CustomerParkingSpot;

public interface CustomerParkingSpotRepository extends JpaRepository<CustomerParkingSpot, Long> {

}