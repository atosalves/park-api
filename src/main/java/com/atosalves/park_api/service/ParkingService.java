package com.atosalves.park_api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.CustomerParkingSpot;
import com.atosalves.park_api.entity.ParkingSpot;
import com.atosalves.park_api.util.ParkingUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParkingService {

        private final CustomerParkingSpotService customerParkingSpotService;
        private final CustomerService customerService;
        private final ParkingSpotService parkingSpotService;

        @Transactional
        public CustomerParkingSpot checkIn(CustomerParkingSpot customerParkingSpot) {
                var customer = customerService.getByCpf(customerParkingSpot.getCustomer().getCpf());
                customerParkingSpot.setCustomer(customer);

                var parkingSpot = parkingSpotService.getByAvailableParkingSpot();
                parkingSpot.setStatus(ParkingSpot.StatusParkingSpot.OCCUPIED);

                customerParkingSpot.setParkingSpot(parkingSpot);

                customerParkingSpot.setEntryAt(LocalDateTime.now());
                customerParkingSpot.setReceipt(ParkingUtils.generateReceipt(customerParkingSpot.getLicensePlate()));

                return customerParkingSpotService.create(customerParkingSpot);
        }

}
