package com.atosalves.park_api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.Customer;
import com.atosalves.park_api.exception.EntityNotFoundException;
import com.atosalves.park_api.exception.UniqueViolationException;
import com.atosalves.park_api.repository.CustomerRepository;
import com.atosalves.park_api.repository.projection.CustomerProjection;

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

        @Transactional(readOnly = true)
        public Customer getById(Long id) {
                return customerRepository.findById(id).orElseThrow(
                                () -> new EntityNotFoundException(String.format("Cliente '%s' não encontrado", id)));
        }

        @Transactional(readOnly = true)
        public Page<CustomerProjection> getAll(Pageable pageable) {
                return customerRepository.findAllPageable(pageable);
        }

        @Transactional(readOnly = true)
        public Customer getByUserId(Long id) {
                return customerRepository.findByUserId(id);
        }

}
