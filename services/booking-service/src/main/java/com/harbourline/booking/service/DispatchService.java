package com.harbourline.booking.service;

import com.harbourline.booking.domain.Booking;
import com.harbourline.booking.domain.Depot;
import com.harbourline.booking.domain.DispatchSlot;
import com.harbourline.booking.events.BookingDispatchedEvent;
import com.harbourline.booking.exception.NoDispatchSlotException;
import com.harbourline.booking.repository.DepotRepository;
import com.harbourline.booking.repository.DispatchSlotRepository;
import java.time.*;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * Books a dock slot at the lane's depot and announces the dispatch to tracking-worker.
 */
@Service
public class DispatchService {

    // Liczba ramp załadunkowych jest w host_vars (loading_docks); tutaj domyślnie 8.
    private static final int DEFAULT_DOCKS = 8;
    // Ile dni do przodu szukamy wolnego slotu, zanim zgłosimy błąd.
    private static final int LOOKAHEAD_DAYS = 5;

    private final DepotRepository depots;
    private final DispatchSlotRepository slots;
    private final BookingEventPublisher events;
    private final Clock clock;

    public DispatchService(DepotRepository depots, DispatchSlotRepository slots, BookingEventPublisher events,
                           Clock clock) {
        this.depots = depots;
        this.slots = slots;
        this.events = events;
        this.clock = clock;
    }

    public DispatchSlot schedule(Booking b, int transitDays) {
        Depot depot = depots.findById(b.getLane().getDepotCode()).orElseThrow();
        ZonedDateTime now = ZonedDateTime.now(clock).withZoneSameInstant(ZoneId.of(depot.getTimezone()));
        // Po godzinie granicznej (cut-off) wysyłka przechodzi na następny dzień roboczy.
        LocalDate first = b.getReadyDate().isAfter(now.toLocalDate()) ? b.getReadyDate()
                : now.toLocalTime().isAfter(depot.getDispatchCutoff()) ? now.toLocalDate().plusDays(1)
                : now.toLocalDate();
        for (int d = 0; d < LOOKAHEAD_DAYS; d++) {
            LocalDate day = skipWeekend(first.plusDays(d));
            Set<Short> used = new HashSet<>();
            slots.findByDepotCodeAndSlotDate(depot.getCode(), day).forEach(s -> used.add(s.getDockNo()));
            for (short dock = 1; dock <= DEFAULT_DOCKS; dock++) {
                if (!used.contains(dock)) {
                    DispatchSlot slot = slots.save(new DispatchSlot(depot.getCode(), day, dock, b.getId()));
                    b.dispatched();
                    events.dispatched(new BookingDispatchedEvent(b.getBookingRef(), depot.getCode(), day, dock,
                            b.getLane().getDestination(), b.getConsignee().getContactName(),
                            b.getConsignee().getPhone(), transitDays, Instant.now(clock)));
                    return slot;
                }
            }
        }
        throw new NoDispatchSlotException(depot.getCode());
    }

    private static LocalDate skipWeekend(LocalDate d) {
        // Sobota i niedziela - brak wysyłek.
        while (d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY) d = d.plusDays(1);
        return d;
    }
}
