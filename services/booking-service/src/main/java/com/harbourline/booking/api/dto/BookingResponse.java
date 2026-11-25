package com.harbourline.booking.api.dto;

import com.harbourline.booking.domain.Booking;
import java.math.BigDecimal;

public record BookingResponse(String bookingRef, String status, BigDecimal quotedTotal, String currency,
                              String customsRef) {
    public static BookingResponse of(Booking b) {
        return new BookingResponse(b.getBookingRef(), b.getStatus().name(), b.getQuotedTotal(), "GBP",
                b.getCustomsRef());
    }
}
