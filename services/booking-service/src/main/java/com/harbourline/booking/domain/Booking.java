package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "booking_id")
    private Long id;
    @Column(name = "booking_ref")
    private String bookingRef;
    @ManyToOne(optional = false) @JoinColumn(name = "shipper_id")
    private Shipper shipper;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST) @JoinColumn(name = "delivery_party_id")
    private Consignee consignee;
    @ManyToOne(optional = false) @JoinColumn(name = "lane_id")
    private Lane lane;
    private String equipment;
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.DRAFT;
    @Column(name = "quoted_total")
    private BigDecimal quotedTotal;
    @Column(name = "customs_ref")
    private String customsRef;
    @Column(name = "customs_status")
    private String customsStatus;
    @Column(name = "ready_date")
    private LocalDate readyDate;
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CargoItem> items = new ArrayList<>();

    protected Booking() { }

    public Booking(String ref, Shipper shipper, Consignee consignee, Lane lane, String equipment, LocalDate readyDate) {
        this.bookingRef = ref;
        this.shipper = shipper;
        this.consignee = consignee;
        this.lane = lane;
        this.equipment = equipment;
        this.readyDate = readyDate;
    }

    public void addItem(CargoItem item) { item.attachTo(this); items.add(item); }
    public void quoted(BigDecimal total) { this.quotedTotal = total; this.status = BookingStatus.QUOTED; }
    public void confirm() { this.status = BookingStatus.CONFIRMED; }
    public void customsRequested(String ref) { this.customsRef = ref; this.customsStatus = "REQUESTED"; this.status = BookingStatus.CUSTOMS_PENDING; }
    public void dispatched() { this.status = BookingStatus.DISPATCHED; }

    public Long getId() { return id; }
    public String getBookingRef() { return bookingRef; }
    public Shipper getShipper() { return shipper; }
    public Consignee getConsignee() { return consignee; }
    public Lane getLane() { return lane; }
    public String getEquipment() { return equipment; }
    public BookingStatus getStatus() { return status; }
    public BigDecimal getQuotedTotal() { return quotedTotal; }
    public String getCustomsRef() { return customsRef; }
    public LocalDate getReadyDate() { return readyDate; }
    public List<CargoItem> getItems() { return items; }
}
