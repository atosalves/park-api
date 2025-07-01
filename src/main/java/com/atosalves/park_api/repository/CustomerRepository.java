package com.atosalves.park_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atosalves.park_api.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
