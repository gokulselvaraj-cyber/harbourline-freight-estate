package com.harbourline.booking.config;

import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** harbourline.rating.* - fuel surcharge is set per profile (see application-prod.yml). */
@ConfigurationProperties(prefix = "harbourline.rating")
public record RatingProperties(BigDecimal fuelSurchargePct, BigDecimal customsDocsFeeGbp, int roadVolumetricKgPerM3) {
}
