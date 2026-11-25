package com.harbourline.booking.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record QuoteRequest(@Size(min = 5, max = 5) String origin, @Size(min = 5, max = 5) String destination,
                           @Pattern(regexp = "SEA|ROAD") String mode, @NotBlank String equipment,
                           @Positive int units, LocalDate onDate, @Valid List<CargoLine> items) {
}
