package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dispatch_slot")
public class DispatchSlot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "slot_id")
    private Long id;
    @Column(name = "depot_code")
    private String depotCode;
    @Column(name = "slot_date")
    private LocalDate slotDate;
    @Column(name = "dock_no")
    private short dockNo;
    @Column(name = "booking_id")
    private Long bookingId;

    protected DispatchSlot() { }

    public DispatchSlot(String depotCode, LocalDate slotDate, short dockNo, Long bookingId) {
        this.depotCode = depotCode;
        this.slotDate = slotDate;
        this.dockNo = dockNo;
        this.bookingId = bookingId;
    }

    public Long getId() { return id; }
    public String getDepotCode() { return depotCode; }
    public LocalDate getSlotDate() { return slotDate; }
    public short getDockNo() { return dockNo; }
    public Long getBookingId() { return bookingId; }
}
