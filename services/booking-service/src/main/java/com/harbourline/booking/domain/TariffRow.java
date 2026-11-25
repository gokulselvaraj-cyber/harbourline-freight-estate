package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lane_tariff")
@IdClass(TariffRow.Key.class)
public class TariffRow {
    @Id private String origin;
    @Id private String destination;
    @Id private String mode;
    @Id private String equipment;
    @Id @Column(name = "weight_band") private short weightBand;
    @Id @Column(name = "valid_from") private LocalDate validFrom;
    @Column(name = "rate_gbp") private BigDecimal rateGbp;
    @Column(name = "transit_days") private short transitDays;

    public BigDecimal getRateGbp() { return rateGbp; }
    public short getTransitDays() { return transitDays; }
    public LocalDate getValidFrom() { return validFrom; }

    public record Key(String origin, String destination, String mode, String equipment, short weightBand,
                      LocalDate validFrom) implements Serializable { }
}
