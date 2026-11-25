package com.harbourline.booking.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CrossBorderPolicyTest {
    private final CrossBorderPolicy p = new CrossBorderPolicy();

    @Test
    void gbToEuNeedsCustoms() { assertThat(p.needsCustoms("GB", "NL")).isTrue(); }

    @Test
    void euToGbNeedsCustoms() { assertThat(p.needsCustoms("DE", "GB")).isTrue(); }

    @Test
    void intraEuDoesNot() { assertThat(p.needsCustoms("NL", "PL")).isFalse(); }

    @Test
    void domesticGbDoesNot() { assertThat(p.needsCustoms("GB", "GB")).isFalse(); }
}
