package com.atosalves.park_api.web.dto.mapper;

import org.springframework.beans.BeanUtils;

import com.atosalves.park_api.entity.Customer;
import com.atosalves.park_api.web.dto.customer.CustomerCreateDto;
import com.atosalves.park_api.web.dto.customer.CustomerResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {

        public static Customer toCustomer(CustomerCreateDto createDto) {
                var customer = new Customer();
                BeanUtils.copyProperties(createDto, customer);
                return customer;
        }

        public static CustomerResponseDto toDto(Customer customer) {
                return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getCpf());
        }

}
