package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cargo_item")
public class CargoItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "cargo_item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "booking_id")
    private Booking booking;
    private String description;
    @Column(name = "hs_code")
    private String hsCode;
    private int packages;
    @Column(name = "gross_kg")
    private BigDecimal grossKg;
    @Column(name = "volume_m3")
    private BigDecimal volumeM3;
    private boolean dangerous;

    protected CargoItem() { }

    public CargoItem(String description, String hsCode, int packages, BigDecimal grossKg,
                     BigDecimal volumeM3, boolean dangerous) {
        this.description = description;
        this.hsCode = hsCode;
        this.packages = packages;
        this.grossKg = grossKg;
        this.volumeM3 = volumeM3;
        this.dangerous = dangerous;
    }

    void attachTo(Booking b) { this.booking = b; }
    public String getDescription() { return description; }
    public String getHsCode() { return hsCode; }
    public int getPackages() { return packages; }
    public BigDecimal getGrossKg() { return grossKg; }
    public BigDecimal getVolumeM3() { return volumeM3; }
    public boolean isDangerous() { return dangerous; }
}
