package com.harbourline.booking.domain;

import java.math.BigDecimal;

public record Quote(String origin, String destination, String equipment, int weightBand, BigDecimal base,
                    BigDecimal fuelSurcharge, BigDecimal customsFee, BigDecimal total, int transitDays) {
}
