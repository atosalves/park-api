package com.atosalves.park_api.util;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

        public static String generateReceipt(String licensePlate) {
                var dateTime = LocalDateTime.now();
                var receipt = dateTime.toString().substring(0, 19);

                return licensePlate + "-" + receipt.replace("-", "").replace(":", "").replace("T", "-");
        }

}
