package com.harbourline.booking.events;

import java.math.BigDecimal;
import java.time.Instant;

public record BookingConfirmedEvent(String bookingRef, String origin, String destination, String mode,
                                    BigDecimal total, boolean customsRequired, Instant at) {
}
