package com.harbourline.booking.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.harbourline.booking.config.RatingProperties;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class RatingServiceTest {

    private final RatingService svc = new RatingService(null,
            new RatingProperties(new BigDecimal("12.5"), new BigDecimal("45.00"), 333), new CrossBorderPolicy());

    @Test
    void volumetricWinsForLightBulkyCargo() {
        assertThat(svc.chargeableRoadKg(new BigDecimal("400"), new BigDecimal("2.0"))).isEqualByComparingTo("666");
    }

    @Test
    void actualWeightWinsForDenseCargo() {
        assertThat(svc.chargeableRoadKg(new BigDecimal("1200"), new BigDecimal("1.5"))).isEqualByComparingTo("1200");
    }

    @Test
    void roundsUpToWholeKg() {
        assertThat(svc.chargeableRoadKg(new BigDecimal("10"), new BigDecimal("1.001"))).isEqualByComparingTo("334");
    }
}
