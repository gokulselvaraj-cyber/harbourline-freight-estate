package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "depot")
public class Depot {
    @Id @Column(name = "depot_code")
    private String code;
    private String name;
    private String country;
    private String locode;
    private String timezone;
    @Column(name = "dispatch_cutoff")
    private LocalTime dispatchCutoff;

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getTimezone() { return timezone; }
    public LocalTime getDispatchCutoff() { return dispatchCutoff; }
}
