package com.atosalves.park_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.CustomerParkingSpot;
import com.atosalves.park_api.exception.EntityNotFoundException;
import com.atosalves.park_api.repository.CustomerParkingSpotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerParkingSpotService {

        private final CustomerParkingSpotRepository customerParkingSpotRepository;

        @Transactional
        public CustomerParkingSpot save(CustomerParkingSpot customerParkingSpot) {
                return customerParkingSpotRepository.save(customerParkingSpot);
        }

        @Transactional(readOnly = true)
        public CustomerParkingSpot getByReceipt(String receipt) {
                return customerParkingSpotRepository.findByReceiptAndExitAtIsNull(receipt).orElseThrow(
                                () -> new EntityNotFoundException(
                                                String.format("Recibo '%s' não encontrado ou check-out já realizado",
                                                                receipt)));
        }

        @Transactional(readOnly = true)
        public long getCompletedParkingsCount(String cpf) {
                return customerParkingSpotRepository.countByCustomerCpfAndExitAtIsNotNull(cpf);
        }

        @Transactional(readOnly = true)
        public Page<CustomerParkingSpot> getAllByCpf(String cpf, Pageable pageable) {
                return customerParkingSpotRepository.findAllByCustomerCpf(cpf, pageable);
        }

}
