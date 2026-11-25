package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * A cargo manifest: the list of bookings loaded on one trailer or one sailing from a depot.
 * Closed by {@code ManifestCloseJob} at the depot's dispatch cut-off.
 */
@Entity
@Table(name = "manifest")
public class Manifest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "manifest_id")
    private Long id;
    @Column(name = "manifest_no")
    private String manifestNo;
    @Column(name = "depot_code")
    private String depotCode;
    @Column(name = "vehicle_ref")
    private String vehicleRef;
    @Column(name = "sailing_date")
    private LocalDate sailingDate;
    private boolean closed;

    public Long getId() { return id; }
    public String getManifestNo() { return manifestNo; }
    public String getDepotCode() { return depotCode; }
    public LocalDate getSailingDate() { return sailingDate; }
    public boolean isClosed() { return closed; }
    public void close() { this.closed = true; }
}
