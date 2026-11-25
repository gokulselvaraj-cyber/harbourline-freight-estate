package com.harbourline.booking.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Weekend handling only; slot allocation is covered by the integration tests (not yet written). */
class DispatchServiceTest {
    @Test
    void saturdayReadyDateMovesToMonday() throws Exception {
        var m = DispatchService.class.getDeclaredMethod("skipWeekend", LocalDate.class);
        m.setAccessible(true);
        LocalDate d = (LocalDate) m.invoke(null, LocalDate.of(2026, 11, 7));
        assertThat(d.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
    }
}
