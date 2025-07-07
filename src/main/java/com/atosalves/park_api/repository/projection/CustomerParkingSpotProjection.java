package com.atosalves.park_api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CustomerParkingSpotProjection {

        String getCustomerCpf();

        String getReceipt();

        String getParkingSpotCode();

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime getEntryAt();

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime getExitAt();

        BigDecimal getPrice();

        BigDecimal getDescount();

        String getLicensePlate();

        String getBrand();

        String getModel();

        String getColor();

}
