package com.harbourline.booking.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "lane")
public class Lane {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "lane_id")
    private Long id;
    private String origin;
    private String destination;
    @Enumerated(EnumType.STRING)
    private TransportMode mode;
    @Column(name = "depot_code")
    private String depotCode;
    private boolean active;

    public Long getId() { return id; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public TransportMode getMode() { return mode; }
    public String getDepotCode() { return depotCode; }
    public boolean isActive() { return active; }
    public String originCountry() { return origin.substring(0, 2); }
    public String destinationCountry() { return destination.substring(0, 2); }
}
