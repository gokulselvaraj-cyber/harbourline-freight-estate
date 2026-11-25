package com.harbourline.booking.repository;

import com.harbourline.booking.domain.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LaneRepository extends JpaRepository<Lane, Long> {
    @Query("select l from Lane l where l.origin = ?1 and l.destination = ?2 and l.mode = ?3 and l.active = true")
    Optional<Lane> findActive(String origin, String destination, TransportMode mode);
}
