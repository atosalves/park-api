package com.atosalves.park_api.web.dto.mapper;

import com.atosalves.park_api.entity.Customer;
import com.atosalves.park_api.entity.CustomerParkingSpot;
import com.atosalves.park_api.web.dto.parking.ParkingCreateDto;
import com.atosalves.park_api.web.dto.parking.ParkingResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerParkingSpotMapper {

        public static CustomerParkingSpot toCustomerParkingSpot(ParkingCreateDto createDto) {
                var customerParkingSpot = new CustomerParkingSpot();

                customerParkingSpot.setLicensePlate(createDto.licensePlate());
                customerParkingSpot.setBrand(createDto.brand());
                customerParkingSpot.setModel(createDto.model());
                customerParkingSpot.setColor(createDto.color());

                var customer = new Customer();
                customer.setCpf(createDto.customerCpf());
                customerParkingSpot.setCustomer(customer);

                return customerParkingSpot;
        }

        public static ParkingResponseDto toDto(CustomerParkingSpot customerParkingSpot) {
                return new ParkingResponseDto(
                                customerParkingSpot.getCustomer().getCpf(),
                                customerParkingSpot.getReceipt(),
                                customerParkingSpot.getParkingSpot().getCode(),
                                customerParkingSpot.getEntryAt(),
                                customerParkingSpot.getExitAt(),
                                customerParkingSpot.getPrice(),
                                customerParkingSpot.getDiscount(),
                                customerParkingSpot.getLicensePlate(),
                                customerParkingSpot.getBrand(),
                                customerParkingSpot.getModel(),
                                customerParkingSpot.getColor());
        }

}
