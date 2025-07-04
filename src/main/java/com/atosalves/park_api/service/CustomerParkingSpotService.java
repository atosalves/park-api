package com.atosalves.park_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.CustomerParkingSpot;
import com.atosalves.park_api.repository.CustomerParkingSpotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerParkingSpotService {

        private final CustomerParkingSpotRepository customerParkingSpotRepository;

        @Transactional
        public CustomerParkingSpot create(CustomerParkingSpot customerParkingSpot) {
                return customerParkingSpotRepository.save(customerParkingSpot);
        }

}
