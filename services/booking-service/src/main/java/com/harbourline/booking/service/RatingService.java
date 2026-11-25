package com.harbourline.booking.service;

import com.harbourline.booking.config.RatingProperties;
import com.harbourline.booking.domain.*;
import com.harbourline.booking.exception.NoTariffException;
import com.harbourline.booking.repository.TariffRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Prices a shipment against lane_tariff.
 * <ul>
 *   <li>FCL / FTL: rate per unit (container or 13.6 m trailer).</li>
 *   <li>Road groupage (GRP): rate per 100 kg of chargeable weight; chargeable = max(actual kg,
 *       volume m3 x 333), rounded UP to the whole kg. customs-docs has its own copy of this rule.</li>
 *   <li>Sea LCL: rate per freight tonne, W/M = max(kg / 1000, m3).</li>
 * </ul>
 * Fuel surcharge (BAF) is a percentage of the base, from harbourline.rating.fuel-surcharge-pct.
 */
@Service
public class RatingService {

    private final TariffRepository tariffs;
    private final RatingProperties props;
    private final CrossBorderPolicy crossBorder;

    public RatingService(TariffRepository tariffs, RatingProperties props, CrossBorderPolicy crossBorder) {
        this.tariffs = tariffs;
        this.props = props;
        this.crossBorder = crossBorder;
    }

    public Quote quote(Lane lane, Equipment equipment, int units, List<CargoItem> items, LocalDate onDate) {
        BigDecimal kg = items.stream().map(CargoItem::getGrossKg).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal m3 = items.stream().map(CargoItem::getVolumeM3).reduce(BigDecimal.ZERO, BigDecimal::add);

        int band = 1;
        BigDecimal qty = BigDecimal.valueOf(units);
        if (equipment == Equipment.GRP) {
            BigDecimal chargeable = chargeableRoadKg(kg, m3);
            band = WeightBand.forChargeableKg(chargeable);
            qty = chargeable.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else if (equipment == Equipment.LCL) {
            BigDecimal tonnes = kg.divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);
            qty = tonnes.max(m3);
            band = WeightBand.forChargeableKg(qty.multiply(BigDecimal.valueOf(1000)));
        }

        TariffRow row = tariffs.findRate(lane.getOrigin(), lane.getDestination(), lane.getMode().name(),
                        equipment.code(), band, onDate)
                .orElseThrow(() -> new NoTariffException(lane.getOrigin() + "-" + lane.getDestination()
                        + " " + equipment.code()));

        BigDecimal base = row.getRateGbp().multiply(qty).setScale(2, RoundingMode.HALF_UP);
        BigDecimal fuel = base.multiply(props.fuelSurchargePct())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal customs = crossBorder.needsCustoms(lane.originCountry(), lane.destinationCountry())
                ? props.customsDocsFeeGbp() : BigDecimal.ZERO;
        BigDecimal total = base.add(fuel).add(customs);
        return new Quote(lane.getOrigin(), lane.getDestination(), equipment.code(), band, base, fuel, customs,
                total, row.getTransitDays());
    }

    /** Road volumetric rule: 1 m3 = 333 kg (1:3), rounded up to whole kg. */
    BigDecimal chargeableRoadKg(BigDecimal actualKg, BigDecimal volumeM3) {
        BigDecimal volumetric = volumeM3.multiply(BigDecimal.valueOf(props.roadVolumetricKgPerM3()));
        return actualKg.max(volumetric).setScale(0, RoundingMode.CEILING);
    }
}
