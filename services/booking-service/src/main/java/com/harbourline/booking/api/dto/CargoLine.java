package com.harbourline.booking.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CargoLine(@NotBlank String description, String hsCode, @Positive int packages,
                        @NotNull @Positive BigDecimal grossKg, @NotNull @PositiveOrZero BigDecimal volumeM3,
                        boolean dangerous) {
}
