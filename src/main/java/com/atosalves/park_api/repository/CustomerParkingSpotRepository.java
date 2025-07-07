package com.atosalves.park_api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.atosalves.park_api.entity.CustomerParkingSpot;

public interface CustomerParkingSpotRepository extends JpaRepository<CustomerParkingSpot, Long> {

        Optional<CustomerParkingSpot> findByReceiptAndExitAtIsNull(String receipt);

        long countByCustomerCpfAndExitAtIsNotNull(String cpf);

        Page<CustomerParkingSpot> findAllByCustomerCpf(String cpf, Pageable pageable);

        Page<CustomerParkingSpot> findAllByCustomerUserId(Long id, Pageable pageable);

}