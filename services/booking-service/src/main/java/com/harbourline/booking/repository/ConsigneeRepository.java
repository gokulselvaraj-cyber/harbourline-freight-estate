package com.harbourline.booking.repository;

import com.harbourline.booking.domain.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsigneeRepository extends JpaRepository<Consignee, Long> {
}
