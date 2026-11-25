package com.harbourline.booking.api;

import com.harbourline.booking.api.dto.BookingResponse;
import com.harbourline.booking.api.dto.CreateBookingRequest;
import com.harbourline.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {
    private final BookingService bookings;

    public BookingController(BookingService bookings) { this.bookings = bookings; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody CreateBookingRequest req) {
        return BookingResponse.of(bookings.createBooking(req));
    }

    @GetMapping("/{bookingRef}")
    public BookingResponse get(@PathVariable String bookingRef) {
        return BookingResponse.of(bookings.get(bookingRef));
    }
}
