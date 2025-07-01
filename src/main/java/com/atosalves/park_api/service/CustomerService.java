package com.atosalves.park_api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.Customer;
import com.atosalves.park_api.exception.UniqueViolationException;
import com.atosalves.park_api.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService {

        private final CustomerRepository customerRepository;

        @Transactional
        public Customer create(Customer customer) {
                try {
                        return customerRepository.save(customer);
                } catch (DataIntegrityViolationException e) {
                        throw new UniqueViolationException(String.format("CPF '%s' já cadastrado", customer.getCpf()));
                }
        }

}
