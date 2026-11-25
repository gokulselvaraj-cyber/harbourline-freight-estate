package com.harbourline.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class WeightBandTest {
    @Test
    void bands() {
        assertThat(WeightBand.forChargeableKg(new BigDecimal("500"))).isEqualTo(1);
        assertThat(WeightBand.forChargeableKg(new BigDecimal("501"))).isEqualTo(2);
        assertThat(WeightBand.forChargeableKg(new BigDecimal("12000"))).isEqualTo(4);
        assertThat(WeightBand.forChargeableKg(new BigDecimal("12001"))).isEqualTo(5);
    }
}
