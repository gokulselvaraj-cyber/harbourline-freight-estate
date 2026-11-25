package com.harbourline.booking.repository;

import com.harbourline.booking.domain.TariffRow;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Reads lane_tariff (2026 rates are seeded by V7__lane_tariff_2026.sql). */
public interface TariffRepository extends JpaRepository<TariffRow, TariffRow.Key> {

    @Query(value = """
            select * from lane_tariff
             where origin = ?1 and destination = ?2 and mode = ?3 and equipment = ?4
               and weight_band = ?5 and valid_from <= ?6
             order by valid_from desc limit 1""", nativeQuery = true)
    Optional<TariffRow> findRate(String origin, String destination, String mode, String equipment,
                                 int weightBand, LocalDate onDate);
}
