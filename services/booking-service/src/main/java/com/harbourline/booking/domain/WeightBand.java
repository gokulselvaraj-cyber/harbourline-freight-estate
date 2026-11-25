package com.harbourline.booking.domain;

import java.math.BigDecimal;

/** Road groupage / LCL weight bands used by lane_tariff.weight_band. FCL and FTL are always band 1. */
public final class WeightBand {
    private WeightBand() { }

    public static int forChargeableKg(BigDecimal kg) {
        long v = kg.longValue();
        if (v <= 500) return 1;
        if (v <= 2000) return 2;
        if (v <= 5000) return 3;
        if (v <= 12000) return 4;
        return 5;
    }
}
