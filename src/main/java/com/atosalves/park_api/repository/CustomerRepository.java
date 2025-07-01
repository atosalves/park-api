package com.atosalves.park_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atosalves.park_api.entity.Customer;
import com.atosalves.park_api.repository.projection.CustomerProjection;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
        @Query("SELECT c FROM Customer c")
        Page<CustomerProjection> findAllPageable(Pageable pageable);

}
