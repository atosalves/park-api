package com.atosalves.park_api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

        private static final double INITIAL_15_MINUTES = 5.00;
        private static final double INITIAL_60_MINUTES = 9.25;
        private static final double ADD_15_MINUTES = 1.75;

        private static final double PERCENTAGE_DISCOUNT = 0.30;

        public static String generateReceipt(String licensePlate) {
                var dateTime = LocalDateTime.now();
                var receipt = dateTime.toString().substring(0, 19);

                return licensePlate + "-" + receipt.replace("-", "").replace(":", "").replace("T", "-");
        }

        public static BigDecimal calculatePrice(LocalDateTime entryAt, LocalDateTime exitAt) {
                long minutes = entryAt.until(exitAt, ChronoUnit.MINUTES);
                double total = 0.0;

                if (minutes <= 15) {
                        total = INITIAL_15_MINUTES;
                } else if (minutes <= 60) {
                        total = INITIAL_60_MINUTES;
                } else {
                        long addMinutes = minutes - 60;
                        Double totalParts = ((double) addMinutes / 15);
                        if (totalParts > totalParts.intValue()) {
                                total += INITIAL_60_MINUTES + (ADD_15_MINUTES * (totalParts.intValue() + 1));
                        } else {
                                total += INITIAL_60_MINUTES + (ADD_15_MINUTES * totalParts.intValue());
                        }
                }

                return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
        }

        public static BigDecimal calculateDiscount(BigDecimal price, long parkingCount) {
                BigDecimal discount = ((parkingCount > 0) && (parkingCount % 10 == 0))
                                ? price.multiply(new BigDecimal(PERCENTAGE_DISCOUNT))
                                : new BigDecimal(0);
                return discount.setScale(2, RoundingMode.HALF_EVEN);
        }

}
