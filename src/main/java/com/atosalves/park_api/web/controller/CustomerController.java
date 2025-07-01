package com.atosalves.park_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atosalves.park_api.jwt.JwtUserDetails;
import com.atosalves.park_api.service.CustomerService;
import com.atosalves.park_api.service.UserService;
import com.atosalves.park_api.web.dto.customer.CustomerCreateDto;
import com.atosalves.park_api.web.dto.customer.CustomerResponseDto;
import com.atosalves.park_api.web.dto.mapper.CustomerMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

        private final CustomerService customerService;
        private final UserService userService;

        @PostMapping
        @PreAuthorize("hasRole('CUSTOMER')")
        public ResponseEntity<CustomerResponseDto> create(@Valid @RequestBody CustomerCreateDto createDto,
                        @AuthenticationPrincipal JwtUserDetails userDetails) {
                var customer = CustomerMapper.toCustomer(createDto);

                var user = userService.getById(userDetails.getId());
                customer.setUser(user);

                customerService.create(customer);

                return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toDto(customer));
        }

}
