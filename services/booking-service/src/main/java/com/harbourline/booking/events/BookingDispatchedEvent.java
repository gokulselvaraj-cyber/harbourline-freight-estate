package com.harbourline.booking.events;

import java.time.Instant;
import java.time.LocalDate;

/** Payload of booking.dispatched.v1 (contracts/tracking-events.asyncapi.yaml). */
public record BookingDispatchedEvent(String bookingRef, String depotCode, LocalDate slotDate, int dockNo,
                                     String destination, String receiverName, String receiverPhone,
                                     int transitDays, Instant at) {
}
