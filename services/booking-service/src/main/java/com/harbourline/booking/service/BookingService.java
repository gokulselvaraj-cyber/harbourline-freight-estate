package com.harbourline.booking.service;

import com.harbourline.booking.api.dto.CreateBookingRequest;
import com.harbourline.booking.domain.*;
import com.harbourline.booking.events.BookingConfirmedEvent;
import com.harbourline.booking.exception.BookingNotFoundException;
import com.harbourline.booking.exception.LaneNotServedException;
import com.harbourline.booking.repository.BookingRepository;
import com.harbourline.booking.repository.LaneRepository;
import com.harbourline.booking.repository.ShipperLookup;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Booking journey: create -> rate -> (customs docs if GB<->EU) -> confirm -> dispatch.
 */
@Service
public class BookingService {

    private final BookingRepository bookings;
    private final LaneRepository lanes;
    private final ShipperLookup shippers;
    private final RatingService rating;
    private final CustomsDocsClient customsDocs;
    private final CrossBorderPolicy crossBorder;
    private final DispatchService dispatch;
    private final BookingEventPublisher events;
    private final ReferenceGenerator refs;
    private final Clock clock;

    public BookingService(BookingRepository bookings, LaneRepository lanes, ShipperLookup shippers,
                          RatingService rating, CustomsDocsClient customsDocs, CrossBorderPolicy crossBorder,
                          DispatchService dispatch, BookingEventPublisher events, ReferenceGenerator refs,
                          Clock clock) {
        this.bookings = bookings;
        this.lanes = lanes;
        this.shippers = shippers;
        this.rating = rating;
        this.customsDocs = customsDocs;
        this.crossBorder = crossBorder;
        this.dispatch = dispatch;
        this.events = events;
        this.refs = refs;
        this.clock = clock;
    }

    @Transactional
    public Booking createBooking(CreateBookingRequest req) {
        TransportMode mode = TransportMode.valueOf(req.mode());
        Lane lane = lanes.findActive(req.origin(), req.destination(), mode)
                .orElseThrow(() -> new LaneNotServedException(req.origin() + "-" + req.destination() + " " + mode));
        Shipper shipper = shippers.byAccount(req.shipperAccount());
        var r = req.receiver();
        Consignee consignee = new Consignee(r.name(), r.company(), r.phone(), r.email(), r.address(), r.city(),
                r.postcode(), r.country(), r.eori());
        Booking b = new Booking(refs.next(LocalDate.now(clock).getYear()), shipper, consignee, lane,
                req.equipment(), req.readyDate());
        req.items().forEach(i -> b.addItem(new CargoItem(i.description(), i.hsCode(), i.packages(), i.grossKg(),
                i.volumeM3(), i.dangerous())));

        Quote q = rating.quote(lane, Equipment.fromCode(req.equipment()), req.units(), b.getItems(), req.readyDate());
        b.quoted(q.total());
        b.confirm();
        bookings.save(b);

        boolean customs = crossBorder.needsCustoms(lane.originCountry(), lane.destinationCountry());
        if (customs) {
            b.customsRequested(customsDocs.requestDeclaration(b));
        }
        events.confirmed(new BookingConfirmedEvent(b.getBookingRef(), lane.getOrigin(), lane.getDestination(),
                mode.name(), q.total(), customs, Instant.now(clock)));
        dispatch.schedule(b, q.transitDays());
        return b;
    }

    @Transactional(readOnly = true)
    public Booking get(String ref) {
        return bookings.findByBookingRef(ref).orElseThrow(() -> new BookingNotFoundException(ref));
    }
}
